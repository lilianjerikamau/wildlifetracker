package models;
import models.Sighting;

import java.util.List;

public interface SightingDao {
    //LIST
    List<Sighting> getAll();
    //all by animal id
    List <Sighting> allByAnimal(int animalId);
    //CREATE
    void add (Sighting sighting);

    //UPDATE
    void update (int id,String ranger, String location, int animalId);

    Sighting findById(int id);



    //DELETE
    void deleteById(int id);
    void clearAllSightings();
}

