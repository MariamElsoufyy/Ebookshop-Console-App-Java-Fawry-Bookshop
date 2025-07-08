package models;

public class MailService {

    public static void sendEbook(Book book, String email) {
        System.out.println("Sending Ebook: " + book.getTitle() + " to " + email);
    }

}
