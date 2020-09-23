package com.example.freechef;

public class Order {

    private String touser;
    private String fromuser;
    private String dish_id;
    private String quantity;
    private String price;

    public Order(String touser, String fromuser, String dish_id, String quantity, String price) {

        this.touser = touser;
        this.fromuser = fromuser;
        this.dish_id = dish_id;
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

    public String getDish() {
        return dish_id;
    }

    public void setDish(String dish_id) {
        this.dish_id = dish_id;
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
