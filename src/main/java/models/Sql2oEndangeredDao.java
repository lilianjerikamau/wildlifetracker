package models;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oEndangeredDao  implements EndangeredDao {
    private final Sql2o sql2o;


    public Sql2oEndangeredDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Endangered endangered) {
        String sql = "INSERT INTO endangered (name) VALUES (:name)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(endangered)
                    .executeUpdate()
                    .getKey();
            endangered.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Endangered> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM endangered")
                    .executeAndFetch(Endangered.class);
        }
    }

    @Override
    public Endangered findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM endangered WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Endangered.class);
        }
    }


    @Override
    public void update(int id, String newName,String newHealth,int newAge){
        String sql = "UPDATE endangered SET name = :name, health = :health,  age = :age WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("health", newHealth)
                    .addParameter("age" ,newAge)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from endangered WHERE id=:id"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllEndangered() {
        String sql = "DELETE from endangered"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Animal> getAllAnimalsByCategory(int endangeredId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM animals WHERE endangeredId = :endangeredId")
                    .addParameter("endangeredId", endangeredId)
                    .executeAndFetch(Animal.class);
        }
    }
}
