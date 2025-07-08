package models;

public class DemoBook extends Book {

    public DemoBook(String isbn, String title, int publishYear) {
        super(isbn, title, publishYear, -1.0);// not for sale, so price is not applicable
    }

    @Override
    public String getType() {
        return "DemoBook";
    }

    @Override
    public String isForSale() {
        return "no";
    }

    @Override
    public double buy(String address, String email, int quantity) {
        System.out.println("DemoBook cannot be purchased.");
        return 0.0; // No price since it's not for sale
    }

}
