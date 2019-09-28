package ru.pchurzin.votesystem.model;

import org.springframework.lang.NonNull;

import java.time.LocalDate;

public class Vote {

    private int userId;

    private int restaurantId;

    @NonNull
    private LocalDate date;

    public Vote(int userId, int restaurantId, LocalDate date) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
