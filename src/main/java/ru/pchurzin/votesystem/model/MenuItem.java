package ru.pchurzin.votesystem.model;

import java.util.Objects;
import java.util.StringJoiner;

public class MenuItem extends BaseEntity {

    private String title;

    private int price;

    public MenuItem() {
    }

    public MenuItem(MenuItem other) {
        super(other);
        this.title = other.getTitle();
        this.price = other.getPrice();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return getId().equals(menuItem.getId()) &&
                getPrice() == menuItem.getPrice() &&
                Objects.equals(getTitle(), menuItem.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getPrice());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MenuItem.class.getSimpleName() + "[", "]")
                .add("id=" + getId())
                .add("title='" + title + "'")
                .add("price=" + price)
                .toString();
    }

    private void checkPrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be < 0" + price);
        }
    }
}
