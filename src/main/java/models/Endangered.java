package models;

import java.util.Objects;

public class Endangered {
    private String name;
    private int id;
    private String health;
    private String age;

    public Endangered(String name,String health,String age){
        this.name = name;
        this.health = health;
        this.age = age;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endangered)) return false;
        Endangered endangered = (Endangered) o;
        return id == endangered.id &&
                Objects.equals(name, endangered.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
