package ru.pchurzin.votesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BaseEntity {

    private Integer id;

    public BaseEntity() {
    }

    public BaseEntity(BaseEntity other) {
        id = other.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }

}
