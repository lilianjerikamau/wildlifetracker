package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class Sql2oAnimalDaoTest {
    private Sql2oAnimalDao animalDao; //ignore me for now. We'll create this soon.
    private Connection conn; //must be sql2o class conn
    @BeforeEach
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        animalDao = new Sql2oAnimalDao(sql2o);
        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Animal animal = new Animal ("cheetah");
        int originalAnimalId = animal.getId();
        animalDao.add(animal);
        assertNotEquals(originalAnimalId, animal.getId()); //how does this work?
    }

    @Test
    public void existingAnimalCanBeFoundById() throws Exception {
        Animal animal = new Animal ("mow the lawn");
        animalDao.add(animal); //add to dao (takes care of saving)
        Animal foundTask = animalDao.findById(animal.getId()); //retrieve
        assertEquals(animal, foundTask); //should be the same
    }
    @Test
    public void addedAnimalsAreReturnedFromgetAll() throws Exception {
        Animal animal = new Animal ("cheetah");
        animalDao.add(animal);
        assertEquals(1, animalDao.getAll().size());
    }

    @Test
    public void noAnimalsReturnsEmptyList() throws Exception {
        assertEquals(0, animalDao.getAll().size());
    }

    @AfterEach
    void tearDown() throws Exception {
            conn.close();
        }

}