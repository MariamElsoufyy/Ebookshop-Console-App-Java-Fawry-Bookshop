
import java.util.ArrayList;
import java.util.Calendar;

import models.Book;
import models.DemoBook;
import models.Ebook;
import models.PaperBook;

public class FawryBookShopTest {
    public static void main(String[] args) {

        BookStore bookStore = new BookStore();
        System.out.println("Fawry BookShop: Starting automated test...");

        ArrayList<Book> inventory = new ArrayList<Book>() {
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

        bookStore.inventory = inventory;

        System.out.println("Fawry BookShop: Inventory initialized.\n");

        System.out.println("Fawry BookShop: viewing available books:");
        bookStore.viewBooks();

        System.out.println("Fawry BookShop: Buying 2 copies of PaperBook ISBN 2");
        PaperBook paperBook = (PaperBook) inventory.stream().filter(b -> b.getIsbn().equals("2")).findFirst().get();
        double paidtest1 = paperBook.buy("home", "", 2);
        System.out
                .println("Fawry BookShop: Total Price = " + paidtest1 + ", Remaining stock = " + paperBook.getStock());

        System.out.println("\nFawry BookShop: Buying Ebook ISBN 3");
        Ebook ebook = (Ebook) inventory.stream().filter(b -> b.getIsbn().equals("3")).findFirst().get();
        double paidTest2 = ebook.buy("mariam@gmail.com", "", 0);
        System.out.println("Fawry BookShop: Total Price = " + paidTest2);

        System.out.println("\nFawry BookShop: Attempting to buy DemoBook ISBN 1");
        DemoBook demoBook = (DemoBook) inventory.stream().filter(b -> b.getIsbn().equals("1")).findFirst().get();
        try {
            demoBook.buy("", "", 0);
        } catch (UnsupportedOperationException e) {
            System.out.println("Fawry BookShop: Expected error - " + e.getMessage());
        }

        System.out.println("Fawry BookShop: Checking for outdated books (older than 5 years) and removing them.");
        int years = 5;
        String removeChoice = "yes";
        BookStore.checkOutdatedBooks(years, removeChoice);
        bookStore.viewBooks();

        System.out.println("Fawry BookShop: Checking for outdated books (older than 3 years) and keeping them.");
        years = 3;
        removeChoice = "no";
        BookStore.checkOutdatedBooks(years, removeChoice);
        bookStore.viewBooks();

        System.out.println("Fawry BookShop: Adding Books to the inventory.");
        System.out.println("Fawry BookShop: Running TestAddBooks cases");

        bookStore.TestAddBooks("paperbook", "9", "Paper Book 4", 2024, 39.99, 10, "");
        bookStore.TestAddBooks("ebook", "10", "Ebook 4", 2023, 15.99, 0, "EPUB");
        bookStore.TestAddBooks("demobook", "11", "Demo Book 3", 2022, 0, 0, "");
        bookStore.TestAddBooks("demo", "11", "Duplicate Demo", 2022, 0, 0, "");
        bookStore.TestAddBooks("paper", "2", "Paper Book 1", 2022, 19.99, 5, "");
        bookStore.TestAddBooks("ebook", "3", "Ebook 1", 2021, 9.99, 0, "PDF");
        bookStore.TestAddBooks("paperbook", "11", "Demo Book 3", 2022, 29.99, 3, "");
        bookStore.TestAddBooks("ebook", "1", "Demo Book 1", 2023, 19.99, 0, "MOBI");
        bookStore.TestAddBooks("paper", "3", "Ebook 1", 2021, 22.0, 7, "");
        bookStore.TestAddBooks("demobook", "12", "Demo Book 4", 2021, 0, 0, "");

    }
}
