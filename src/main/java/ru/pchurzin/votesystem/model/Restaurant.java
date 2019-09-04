package ru.pchurzin.votesystem.model;

import java.util.Objects;
import java.util.StringJoiner;

public class Restaurant {

    private Integer id;

    private String title;

    public Restaurant() {

    }

    public Restaurant(Restaurant other) {
        id = other.getId();
        title = other.getTitle();
    }

    public Integer getId() {
        return id;
    }

    public Restaurant setId(Integer id) {
        this.id = id;
        return this;
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
                .add("id=" + id)
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
}
