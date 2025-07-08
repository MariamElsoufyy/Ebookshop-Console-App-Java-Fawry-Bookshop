package models;

public abstract class Book {
    private String isbn;
    private String title;
    private int publishYear;
    private double price;

    public Book(String isbn, String title, int publishYear, double price) {
        this.isbn = isbn;
        this.title = title;
        this.publishYear = publishYear;
        this.price = price;

    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getType();

    public abstract String isForSale();

    public abstract double buy(String address, String email, int quantity); // For PaperBooks, it will be an address;
                                                                            // for Ebooks, it will be an email

}
