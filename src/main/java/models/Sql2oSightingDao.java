package models;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oSightingDao implements SightingDao {
    private final Sql2o sql2o;


    public Sql2oSightingDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Sighting sighting) {
        String sql = "INSERT INTO sightings ( ranger ) VALUES (:ranger)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(sighting)
                    .executeUpdate()
                    .getKey();
            sighting.setSightingId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


    @Override
    public List<Sighting> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM sightings")
                    .executeAndFetch(Sighting.class);
        }
    }

    @Override
    public Sighting findById (int sightingId) {
        String sql = "SELECT * FROM sightings WHERE sightingId = :sightingId;";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("sightingId", sightingId)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Sighting.class);
        }
    }
@Override
public Sighting allByAnimal (int animalId) {
    String sql = "SELECT * FROM sightings WHERE sightingId = :sightingId;";

    try (Connection con = sql2o.open()) {
        return con.createQuery(sql)
                .addParameter("sightingId", animalId)
                .executeAndFetchFirst(Sighting.class);
    }
}

    @Override
    public void update (int sightingId, String newRanger, String newLocation, int newAnimalId){
        String sql =  "UPDATE sightings SET ranger = :ranger, location = :location,  animalId = :animalId WHERE sightingId=:sightingId";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("ranger", newRanger)
                    .addParameter("location", newLocation)
                    .addParameter("animalId", newAnimalId)
                    .addParameter("sightingId", sightingId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int sightingId) {
        String sql = "DELETE from sightings WHERE sightingId=:sightingId"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("sightingId", sightingId)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllSightings() {
        String sql = "DELETE from sightings"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    }
