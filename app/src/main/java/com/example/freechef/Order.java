package com.example.freechef;

public class Order {

    private String touser;
    private String fromuser;
    private Dish dish;
    private String quantity;
    private String price;

    public Order(String touser, String fromuser, Dish dish, String quantity, String price) {

        this.touser = touser;
        this.fromuser = fromuser;
        this.dish = dish;
        this.quantity = quantity;
        this.price = price;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getFromuser() {
        return fromuser;
    }

    public void setFromuser(String fromuser) {
        this.fromuser = fromuser;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
