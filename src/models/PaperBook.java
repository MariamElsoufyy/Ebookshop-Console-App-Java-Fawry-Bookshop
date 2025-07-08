package models;

public class PaperBook extends Book {
    private int stock;

    public PaperBook(String isbn, String title, int publishYear, double price, int stock) {
        super(isbn, title, publishYear, price);
        this.stock = stock;
    }

    @Override
    public String getType() {
        return "PaperBook";
    }

    @Override
    public String isForSale() {
        return "yes";
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isAvailable() {
        return stock > 0;
    }

    @Override
    public double buy(String address, String email, int quantity) {
        if (stock - quantity < 0) {
            throw new IllegalArgumentException(
                    "Not enough stock available for purchase. Requested: " + quantity + ", Available: " + stock);
        }
        this.stock -= quantity;
        System.out.println("Paper book purchased: " + getTitle() + " (Quantity: " + quantity + ")");
        ShippingService.shipPaperBook(this, address);
        return quantity * getPrice();

    }

}
