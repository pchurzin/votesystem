package ru.pchurzin.votesystem.model;

import java.util.Objects;
import java.util.StringJoiner;

public class MenuItem extends BaseEntity {

    private String title;

    private int price;

    private Integer restaurantId;

    public MenuItem() {
    }

    public MenuItem(MenuItem other) {
        super(other);
        this.title = other.getTitle();
        this.price = other.getPrice();
        this.restaurantId = other.getRestaurantId();
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

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(getId(), menuItem.getId()) &&
                getPrice() == menuItem.getPrice() &&
                Objects.equals(getRestaurantId(), menuItem.getRestaurantId()) &&
                Objects.equals(getTitle(), menuItem.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getPrice(), getRestaurantId());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MenuItem.class.getSimpleName() + "[", "]")
                .add("id=" + getId())
                .add("title='" + title + "'")
                .add("price=" + price)
                .add("restaurantId=" + restaurantId)
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

        private Integer restaurantId;

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

        public Builder withRestaurantId(Integer id) {
            restaurantId = id;
            return this;
        }

        public MenuItem build() {
            MenuItem menuItem = new MenuItem();
            menuItem.setId(id);
            menuItem.setTitle(title);
            menuItem.setPrice(price);
            menuItem.setRestaurantId(restaurantId);
            return menuItem;
        }
    }
}
