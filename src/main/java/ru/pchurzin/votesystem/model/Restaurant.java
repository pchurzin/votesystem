package ru.pchurzin.votesystem.model;

import java.util.Objects;
import java.util.StringJoiner;

public class Restaurant extends BaseEntity {

    private String title;

    public Restaurant() {
    }

    public Restaurant(Restaurant other) {
        super(other);
        title = other.getTitle();
    }

    public String getTitle() {
        return title;
    }

    public Restaurant setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Restaurant.class.getSimpleName() + "[", "]")
                .add("id=" + getId())
                .add("title='" + title + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant)) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }

    public static class Builder {

        private Integer id;

        private String title;

        public Builder() {
        }

        public Builder(Restaurant restaurant) {
            id = restaurant.getId();
            title = restaurant.getTitle();
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Restaurant build() {
            Restaurant restaurant = new Restaurant();
            restaurant.setTitle(title);
            restaurant.setId(id);
            return restaurant;
        }
    }
}
