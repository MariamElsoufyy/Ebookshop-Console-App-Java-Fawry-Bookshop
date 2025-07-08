package models;

public class Ebook extends Book {
    private String fileFormat;

    public Ebook(String isbn, String title, int publishYear, double price, String fileFormat) {
        super(isbn, title, publishYear, price);
        this.fileFormat = fileFormat;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    @Override
    public String getType() {
        return "Ebook";
    }

    @Override
    public String isForSale() {
        return "yes";
    }

    public double buy(String email, String address, int quantity) {

        System.out.println("Ebook purchased: " + getTitle() + " (Format: " + fileFormat + ")");
        MailService.sendEbook(this, email);
        return getPrice();

    }

}
