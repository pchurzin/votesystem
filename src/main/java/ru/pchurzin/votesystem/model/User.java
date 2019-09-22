package ru.pchurzin.votesystem.model;

import java.util.Objects;

public class User extends BaseEntity {

    private String name;

    private String password;

    public User() {

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(User other) {
        super(other);
        this.name = other.getName();
        this.password = other.getPassword();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getName(), user.getName()) &&
                Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPassword());
    }

    public static class Builder {

        private Integer id;

        private String name;

        private String password;

        public Builder() {

        }

        public Builder(User other) {
            this.id = other.getId();
            this.name = other.getName();
            this.password = other.getPassword();
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            User user = new User(name, password);
            user.setId(id);
            return user;
        }
    }
}
