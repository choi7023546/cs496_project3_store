package com.example.q.project3_store;

public class Menu {

    private String menu;
    // Store the release date of the movie
    private String price;

    // Constructor that is used to create an instance of the Movie object
    public Menu(String menu, String price) {
        this.menu = menu;
        this.price = price;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
