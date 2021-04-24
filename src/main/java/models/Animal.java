package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Animal {
    private int id;
    private String name;
    private boolean endangered;
    private LocalDateTime createdAt;

    public  Animal(String name){
        this.name= name;
        this.endangered= false;
        this.createdAt = LocalDateTime.now();
    }
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return getEndangered() == animal.getEndangered() &&
                getId() == animal.getId() &&
                Objects.equals(getName(), animal.getName());
    }

    @Override
    public int hashCode() {
            return Objects.hash(getName(), getEndangered(), getId());
        }


        public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean getEndangered() {
        return endangered;
    }

    public void setEndangered(boolean endangered) {
        this.endangered = endangered;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
