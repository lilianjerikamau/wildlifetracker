package models;
import models.Endangered;
import models.Animal;
import java.util.List;

public interface EndangeredDao {

    //LIST
    List<Endangered> getAll();

    //CREATE
    void add (Endangered endangered);

    //READ

    //READ
    Endangered findById(int id);
    List<Animal> getAllAnimalsByCategory(int endangeredId);

    //UPDATE
    void update(int id, String name,String health,int age);


    //DELETE
    void deleteById(int id);
    void clearAllEndangered();
}
