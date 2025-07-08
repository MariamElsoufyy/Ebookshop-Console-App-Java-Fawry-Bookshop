import java.util.ArrayList;
import java.util.Optional;

import models.Book;

public class BookStore {
    static String choice = "";
    static ArrayList<Book> inventory = new ArrayList<Book>() {
        {
            add(new models.DemoBook("1", "Demo Book 1", 2023));
            add(new models.PaperBook("2", "Paper Book 1", 2022, 19.99, 10));
            add(new models.Ebook("3", "Ebook 1", 2021, 9.99, "PDF"));
            add(new models.PaperBook("4", "Paper Book 2", 2020, 29.99, 5));
            add(new models.Ebook("5", "Ebook 2", 2019, 14.99, "EPUB"));
            add(new models.PaperBook("6", "Paper Book 3", 2018, 24.99, 0));
            add(new models.Ebook("7", "Ebook 3", 2017, 12.99, "PDF"));
            add(new models.DemoBook("8", "Demo Book 2", 2020));
        }
    };

    public static void main(String[] args) throws Exception {

        System.out.println("Welcome to Fawry Bookshop!");
        do {

            System.out.println("Please choose an option:");
            System.out.println("1. View all books");
            System.out.println("2. Buy a book");
            System.out.println("3. Add a book");
            System.out.println("4. Check outdates books");
            System.out.println("5. Exit or type 'x' to exit");
            choice = System.console().readLine();

            choice = choice.toLowerCase();

            switch (choice) {
                case "1":
                    viewBooks();
                    break;

                case "2":
                    viewBooks();
                    System.out.println("Enter the ISBN of the book you want to buy:");
                    String isbn = System.console().readLine();
                    buyBook(isbn);
                    break;
                case "3":
                    addBook();
                    break;
                case "4":
                    System.out.println(
                            "Outdate books are the books that passed certain number of years since their publish year.");
                    System.out.println("Enter the number of years to check for outdated books:");
                    int years = Integer.parseInt(System.console().readLine());
                    System.out.println("Do you want to remove outdated books from the inventory? (yes/no)");
                    String removeChoice = System.console().readLine();
                    checkOutdatedBooks(years, removeChoice);
                    break;

                default:
                    break;
            }

        } while (!choice.equals("x") || !choice.equals("5"));
    }

    static public void viewBooks() {
        System.out.println("Available books:");
        for (Book book : inventory) {
            System.out.print(book.getType() + ":" + book.getTitle() + " (ISBN: " + book.getIsbn()
                    + ") , Year: " + book.getPublishYear() + ", For Sale: "
                    + book.isForSale());
            if (book.isForSale() == "yes") {
                System.out.print(", Price: " + book.getPrice() + "\n");
            } else {
                System.out.print("\n");
            }
        }

    }

    static public void buyBook(String isbn) {
        for (Book book : inventory) {
            if (book.getIsbn().equals(isbn)) {
                if (book.isForSale() == "yes") {
                    if (book instanceof models.Ebook) {
                        System.out.println("Enter your email to receive the ebook:");
                        String email = System.console().readLine();
                        double totalPrice = ((models.Ebook) book).buy(email, "", 0);
                        System.out.println("Total price: " + totalPrice);
                        System.out.println("Thank you for Shopping at Fawry BookShop!");
                    } else if (book instanceof models.PaperBook) {
                        if (!((models.PaperBook) book).isAvailable()) {
                            System.out.println("The book is out of stock.");
                            return;
                        }
                        System.out.println("Enter the quantity you want to buy:");
                        int quantity = Integer.parseInt(System.console().readLine());
                        System.out.println("Enter your address for shipping:");
                        String address = System.console().readLine();
                        double totalPrice = -1;
                        try {
                            totalPrice = ((models.PaperBook) book).buy(address, "", quantity);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            return;
                        }

                        if (totalPrice != -1) {
                            System.out.println("Total price: " + totalPrice);
                            System.out.println("Thank you for Shopping at Fawry BookShop!");
                        }

                    }

                } else {
                    System.out.println("The book is not for sale.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }

    static public void addBook() {

        System.out.println("Enter book type:");
        String bookType = System.console().readLine().toLowerCase();
        while (!(bookType.equals("demobook") || bookType.equals("demo") || bookType.equals("d")
                || bookType.equals("paperbook") || bookType.equals("paper") || bookType.equals("p")
                || bookType.equals("ebook") || bookType.equals("e"))) {
            System.out.println("Invalid book type. Please enter demobook, paperbook, or ebook.");
            bookType = System.console().readLine().toLowerCase();
        }
        System.out.println("Enter ISBN:");
        String isbn = System.console().readLine();
        Optional<Book> existingBook = inventory.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();
        if (existingBook.isPresent()) {
            if (existingBook.get() instanceof models.DemoBook
                    && (bookType.equals("demobook") || bookType.equals("demo") || bookType.equals("d"))) {
                System.out.println("Demo book with this ISBN already exists. Cannot add another demo book.");
                return;
            } else if (existingBook.get() instanceof models.PaperBook
                    && (bookType.equals("paperbook") || bookType.equals("paper") || bookType.equals("p"))) {
                System.out.println("Paper book with this ISBN already exists. Updating stock instead.");
                System.out.println("Enter added quantity:");
                int newStock = Integer.parseInt(System.console().readLine());
                ((models.PaperBook) existingBook.get())
                        .setStock(((models.PaperBook) existingBook.get()).getStock() + newStock);
                System.out
                        .println("Stock updated. Current stock: " + ((models.PaperBook) existingBook.get()).getStock());
                return;
            } else if (existingBook.get() instanceof models.Ebook
                    && (bookType.equals("ebook") || bookType.equals("e"))) {
                System.out.println("Ebook with this ISBN already exists.");
                return;
            } else {
                System.out.println("the " + existingBook.get().getType() + " version of this book already exists.");
                if (bookType != "demobook" && bookType != "demo" && bookType != "d") {
                    System.out.println("Enter price:");
                    double price = Double.parseDouble(System.console().readLine());
                    if (bookType.equals("ebook") || bookType.equals("e")) {
                        System.out.println("Enter file format:");
                        String fileFormat = System.console().readLine();
                        models.Ebook newBook = new models.Ebook(isbn, existingBook.get().getTitle(),
                                existingBook.get().getPublishYear(), price, fileFormat);
                        inventory.add(newBook);
                        System.out.println("Book added: " + newBook.getType() + " | " + newBook.getTitle()
                                + " | ISBN: " + newBook.getIsbn() + " | Year: " + newBook.getPublishYear()
                                + " | Price: " + newBook.getPrice());
                    } else if (bookType.equals("paperbook") || bookType.equals("paper") || bookType.equals("p")) {
                        System.out.println("Enter stock:");
                        int stock = Integer.parseInt(System.console().readLine());
                        models.PaperBook newBook = new models.PaperBook(isbn, existingBook.get().getTitle(),
                                existingBook.get().getPublishYear(), price, stock);
                        inventory.add(newBook);
                        System.out.println("Book added: " + newBook.getType() + " | " + newBook.getTitle()
                                + " | ISBN: " + newBook.getIsbn() + " | Year: " + newBook.getPublishYear()
                                + " | Price: " + newBook.getPrice());
                    }
                } else {
                    models.DemoBook newBook = new models.DemoBook(isbn, existingBook.get().getTitle(),
                            existingBook.get().getPublishYear());
                    inventory.add(newBook);
                    System.out.println("Book added: " + newBook.getType() + " | " + newBook.getTitle()
                            + " | ISBN: " + newBook.getIsbn() + " | Year: " + newBook.getPublishYear());
                }
                return;
            }
        }

        System.out.println("Enter title:");
        String title = System.console().readLine();
        System.out.println("Enter publish year:");
        int publishYear = Integer.parseInt(System.console().readLine());
        if (bookType != "demobook" && bookType != "demo") {
            System.out.println("Enter price:");
            double price = Double.parseDouble(System.console().readLine());
            if (bookType.equals("ebook") || bookType.equals("e")) {
                System.out.println("Enter file format:");
                String fileFormat = System.console().readLine();
                models.Ebook newBook = new models.Ebook(isbn, title, publishYear, price, fileFormat);
                inventory.add(newBook);
                System.out.println("Book added: " + newBook.getType() + " | " + newBook.getTitle()
                        + " | ISBN: " + newBook.getIsbn() + " | Year: " + newBook.getPublishYear()
                        + " | Price: " + newBook.getPrice());
            } else if (bookType.equals("paperbook") || bookType.equals("paper")) {
                System.out.println("Enter stock:");
                int stock = Integer.parseInt(System.console().readLine());
                models.PaperBook newBook = new models.PaperBook(isbn, title, publishYear, price, stock);
                inventory.add(newBook);
                System.out.println("Book added: " + newBook.getType() + " | " + newBook.getTitle()
                        + " | ISBN: " + newBook.getIsbn() + " | Year: " + newBook.getPublishYear()
                        + " | Price: " + newBook.getPrice());
            }
        } else {
            models.DemoBook newBook = new models.DemoBook(isbn, title, publishYear);
            inventory.add(newBook);
            System.out.println("Book added: " + newBook.getType() + " | " + newBook.getTitle()
                    + " | ISBN: " + newBook.getIsbn() + " | Year: " + newBook.getPublishYear());
        }
    }

    static public void checkOutdatedBooks(int years, String removeChoice) {
        ArrayList<Book> outdatedBooks = new ArrayList<Book>();

        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        System.out.println("Outdated books:");
        for (Book book : inventory) {
            if (currentYear - book.getPublishYear() >= years) {
                System.out.println(book.getType() + " : " + book.getTitle() + " (ISBN: " + book.getIsbn()
                        + " ), Year : " + book.getPublishYear());
                outdatedBooks.add(book);
            }
        }

        if (outdatedBooks.isEmpty()) {
            System.out.println("No outdated books found.");
        } else {
            System.out.println("Total outdated books: " + outdatedBooks.size());
        }

        if (removeChoice.equalsIgnoreCase("yes")) {
            inventory.removeAll(outdatedBooks);
            System.out.println("Outdated books are removed from the inventory.");
        } else {
            System.out.println("Outdated books are not removed from the inventory.");
        }

    }

    public static void TestAddBooks(
            String bookType,
            String isbn,
            String title,
            int publishYear,
            double price,
            int stock,
            String fileFormat) {
        bookType = bookType.toLowerCase();

        Optional<Book> existingBook = inventory.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();

        if (existingBook.isPresent()) {
            if (existingBook.get() instanceof models.DemoBook
                    && (bookType.equals("demobook") || bookType.equals("demo") || bookType.equals("d"))) {
                System.out.println("Demo book with this ISBN already exists. Cannot add another demo book.");
                return;
            } else if (existingBook.get() instanceof models.PaperBook
                    && (bookType.equals("paperbook") || bookType.equals("paper") || bookType.equals("p"))) {
                System.out.println("Paper book with this ISBN already exists. Updating stock instead.");
                ((models.PaperBook) existingBook.get())
                        .setStock(((models.PaperBook) existingBook.get()).getStock() + stock);
                System.out
                        .println("Stock updated. Current stock: " + ((models.PaperBook) existingBook.get()).getStock());
                return;
            } else if (existingBook.get() instanceof models.Ebook
                    && (bookType.equals("ebook") || bookType.equals("e"))) {
                System.out.println("Ebook with this ISBN already exists.");
                return;
            } else {
                System.out.println("the " + existingBook.get().getType() + " version of this book already exists.");
                if (bookType != "demobook" && bookType != "demo" && bookType != "d") {
                    if (bookType.equals("ebook") || bookType.equals("e")) {
                        models.Ebook newBook = new models.Ebook(isbn, existingBook.get().getTitle(),
                                existingBook.get().getPublishYear(), price, fileFormat);
                        inventory.add(newBook);
                        System.out.println("Book added: " + newBook.getType() + " | " + newBook.getTitle()
                                + " | ISBN: " + newBook.getIsbn() + " | Year: " + newBook.getPublishYear()
                                + " | Price: " + newBook.getPrice());
                    } else if (bookType.equals("paperbook") || bookType.equals("paper") || bookType.equals("p")) {
                        models.PaperBook newBook = new models.PaperBook(isbn, existingBook.get().getTitle(),
                                existingBook.get().getPublishYear(), price, stock);
                        inventory.add(newBook);
                        System.out.println("Book added: " + newBook.getType() + " | " + newBook.getTitle()
                                + " | ISBN: " + newBook.getIsbn() + " | Year: " + newBook.getPublishYear()
                                + " | Price: " + newBook.getPrice());
                    }
                } else {
                    models.DemoBook newBook = new models.DemoBook(isbn, existingBook.get().getTitle(),
                            existingBook.get().getPublishYear());
                    inventory.add(newBook);
                    System.out.println("Book added: " + newBook.getType() + " | " + newBook.getTitle()
                            + " | ISBN: " + newBook.getIsbn() + " | Year: " + newBook.getPublishYear());
                }
                return;
            }
        }

        if (bookType != "demobook" && bookType != "demo") {
            if (bookType.equals("ebook") || bookType.equals("e")) {
                models.Ebook newBook = new models.Ebook(isbn, title, publishYear, price, fileFormat);
                inventory.add(newBook);
                System.out.println("Book added: " + newBook.getType() + " | " + newBook.getTitle()
                        + " | ISBN: " + newBook.getIsbn() + " | Year: " + newBook.getPublishYear()
                        + " | Price: " + newBook.getPrice());
            } else if (bookType.equals("paperbook") || bookType.equals("paper")) {
                models.PaperBook newBook = new models.PaperBook(isbn, title, publishYear, price, stock);
                inventory.add(newBook);
                System.out.println("Book added: " + newBook.getType() + " | " + newBook.getTitle()
                        + " | ISBN: " + newBook.getIsbn() + " | Year: " + newBook.getPublishYear()
                        + " | Price: " + newBook.getPrice());
            }
        } else {
            models.DemoBook newBook = new models.DemoBook(isbn, title, publishYear);
            inventory.add(newBook);
            System.out.println("Book added: " + newBook.getType() + " | " + newBook.getTitle()
                    + " | ISBN: " + newBook.getIsbn() + " | Year: " + newBook.getPublishYear());
        }
    }

}
