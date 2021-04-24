package models;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oAnimalDao implements AnimalDao {
    private final Sql2o sql2o;

    public Sql2oAnimalDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    @Override
    public void add(Animal animal) {
        String sql = "INSERT INTO animals (name) VALUES (:name)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(animal) //map my argument onto the query so we can use information from it
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            animal.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Animal> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM animals") //raw sql
                    .executeAndFetch(Animal.class); //fetch a list
        }
    }

    @Override
    public Animal findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM animals WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Animal.class); //fetch an individual item
        }
    }
    @Override

    public void update(int id, String newDescription){
        String sql = "UPDATE tasks SET description = :description WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("description", newDescription)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void deleteById(int id) {
        String sql = "DELETE from tasks WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllTasks() {
        String sql = "DELETE from tasks";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }


}
