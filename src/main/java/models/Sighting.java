package models;

import org.sql2o.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Sighting {
    private int id;
    private String ranger;
    private String location;
    private int animalId;


    public Sighting(String ranger, String location, int animalId) {
        this.ranger = ranger;
        this.location = location;
        this.animalId = animalId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRanger() {
        return ranger;
    }

    public void setRanger(String ranger) {
        this.ranger = ranger;
    }

    public String getLocation() {
        return location;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public void setLocation(String location) {
        this.location = location;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sighting)) return false;
        Sighting sighting = (Sighting) o;
        return getId() == sighting.getId() && getAnimalId() == sighting.getAnimalId() && Objects.equals(getRanger(), sighting.getRanger()) && Objects.equals(getLocation(), sighting.getLocation());
    }

    public int hashCode() {
        return Objects.hash(getRanger(), getRanger(), getAnimalId(),getId());
    }
}




