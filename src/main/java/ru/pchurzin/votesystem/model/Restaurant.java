package ru.pchurzin.votesystem.model;

public class Restaurant {

    private Integer id;

    private String title;

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
}
