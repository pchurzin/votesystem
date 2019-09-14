package ru.pchurzin.votesystem.model;

public class MenuItem {

    private Integer id;

    private String title;

    private int price;

    public MenuItem() {
    }

    public MenuItem(MenuItem other) {
        this.id = other.getId();
        this.title = other.getTitle();
        this.price = other.getPrice();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        checkPrice(price);
        this.price = price;
    }

    private void checkPrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be < 0" + price);
        }
    }
}
