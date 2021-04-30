package models;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

public class Sql2oAnimalDaoTest {
    private static Sql2oAnimalDao animalDao; //these variables are now static.
    private static Connection conn; //these variables are now static.

    @BeforeAll //changed to @BeforeClass (run once before running any tests in this file)
    public static void setUp() throws Exception { //changed to static
        String connectionString = "jdbc:postgresql://localhost:5432/wildlifetracker_test"; // connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "sherry", "password"); // changed user and pass to null
        animalDao = new Sql2oAnimalDao(sql2o);
        conn = sql2o.open(); // open connection once before this test file is run
    }

    @AfterEach // run after every test
    public void tearDown() throws Exception { //I have changed
        System.out.println("clearing database");
        animalDao.clearAllAnimals(); // clear all tasks after every test
    }

    @AfterAll // changed to @AfterClass (run once after all tests in this file completed)
    public static void shutDown() throws Exception { //changed to static and shutDown
        conn.close(); // close connection once after this entire test file is finished
        System.out.println("connection closed");
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

    @Test
    public void addingAnimalSetsId() throws Exception {
        Animal animal = setupNewAnimal();
        int originalAnimalId = animal.getId();
        animalDao.add(animal);
        assertNotEquals(originalAnimalId, animal.getId());
    }

    //define the following once and then call it as above in your tests.


    @Test
    public void categoryIdIsReturnedCorrectly() throws Exception {
        Animal animal = setupNewAnimal();
        int originalAnimalId = animal.getEndangeredId();
        animalDao.add(animal);
        assertEquals(originalAnimalId, animalDao.findById(animal.getId()).getEndangeredId());
    }

    //define the following once and then call it as above in your tests.

    public Animal setupNewAnimal(){
        return new Animal("lion");
    }

}