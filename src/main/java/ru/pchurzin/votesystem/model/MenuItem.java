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

    public static class Builder {

        private Integer id;

        private String title;

        private int price;

        public Builder() {
        }

        public Builder(MenuItem other) {
            id = other.getId();
            title = other.getTitle();
            price = other.getPrice();
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withPrice(int price) {
            this.price = price;
            return this;
        }

        public MenuItem build() {
            MenuItem menuItem = new MenuItem();
            menuItem.setId(id);
            menuItem.setTitle(title);
            menuItem.setPrice(price);
            return menuItem;
        }
    }
}
