package models;

public class ShippingService {

    public static void shipPaperBook(Book book, String address) {
        System.out.println("Shipping paper book: " + book.getTitle() + " to " + address);
    }

}
