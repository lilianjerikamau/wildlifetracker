package models;

import org.sql2o.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Sighting{
    private String ranger;
    private String location;
    private int animalId;
    private int id;

    public Sighting (String ranger, String location, int animalId){
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
        return id == sighting.id &&
                Objects.equals(ranger, sighting.ranger);
    }

    @Override
//    public int hashCode() {
//        return Objects.hash(ranger, sightingId);
//    }
    public int hashCode() {
        return Objects.hash(getRanger(), getRanger(), getAnimalId());
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(location, sightingId);
//    }
}

    //    public void save() {
//        try(Connection con = DB.sql2o.open()) {
//            String sql = "INSERT INTO sightings (ranger, location, animal_id, date_sighted) VALUES (:ranger, :location, :animal_id, now())";
//            this.id = (int) con.createQuery(sql, true)
//                    .addParameter("ranger", this.ranger)
//                    .addParameter("location", this.location)
//                    .addParameter("animal_id", this.animal_id)
//                    .executeUpdate()
//                    .getKey();
//        }
//    }
//
//    public static List<Sighting> all() {
//        try(Connection con = DB.sql2o.open()) {
//            return con.createQuery(sql)
//                    .executeAndFetch(Sighting.class);
//        }
//    }

