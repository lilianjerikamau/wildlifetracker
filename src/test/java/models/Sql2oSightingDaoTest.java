package models;

import org.junit.jupiter.api.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Sql2oSightingDaoTest {
    private static Sql2oAnimalDao animalDao; //these variables are now static.
    private static Sql2oEndangeredDao endangeredDao;
    private static Sql2oSightingDao sightingDao;
    private static Connection conn; //these variables are now static.

    @BeforeAll //changed to @BeforeClass (run once before running any tests in this file)
    public static void setUp() throws Exception { //changed to static
        String connectionString = "jdbc:postgresql://localhost:5432/wildlifetracker_test"; // connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "sherry", "password"); // changed user and pass to null
        animalDao = new Sql2oAnimalDao(sql2o);
        endangeredDao = new Sql2oEndangeredDao(sql2o);
        sightingDao = new Sql2oSightingDao(sql2o);
        conn = sql2o.open(); // open connection once before this test file is run
    }

    @AfterEach // run after every test
    public void tearDown() throws Exception { //I have changed
        System.out.println("clearing database");
        animalDao.clearAllAnimals(); // clear all tasks after every test
        endangeredDao.clearAllEndangered();
        sightingDao.clearAllSightings();
    }

    @AfterAll // changed to @AfterClass (run once after all tests in this file completed)
    public static void shutDown() throws Exception { //changed to static and shutDown
        conn.close(); // close connection once after this entire test file is finished
        System.out.println("connection closed");
    }
    @Test
    public void addingSightingSetsId() throws Exception {
        Sighting sighting = setupNewSighting();
        int originalSightingId = sighting.getId();
        sightingDao.add(sighting);
        assertNotEquals(originalSightingId, sighting.getId());
    }
    @Test
    public void existingSightingCanBeFoundById() throws Exception {
        Sighting sighting = new Sighting("ken","beach",3);
        sightingDao.add(sighting);
        Sighting foundSighting = sightingDao.findById(sighting.getId());
        assertEquals(sighting, foundSighting);
    }
    @Test
    public void existingSightingCanBeFoundByAnimalId() throws Exception {
        Sighting sighting = setupNewSighting();
        sightingDao.add(sighting);
        assertEquals(1, sightingDao.allByAnimal(2).size());
    }
    @Test
    public void addedSightingAreReturnedFromGetAll() throws Exception {
        Sighting sighting = setupNewSighting();
        sightingDao.add(sighting);
        assertEquals(1, sightingDao.getAll().size());
    }

    @Test
    public void noEndangeredReturnsEmptyList() throws Exception {
        assertEquals(0, sightingDao.getAll().size());
    }
    @Test
    public void addSighting_true() throws Exception {
        String ranger = "kamau";
        String location = "location A";
        int animalId = 2;
        Sighting newSighting = new Sighting(ranger,location,animalId);
        sightingDao.add(newSighting);
    }

    @Test
    public void updateChangesEndangeredContent() throws Exception {
        String initialRanger = "kamau";
        String initialLocation = "river";
        int initialAnimalId = 2;
        Sighting sighting = new Sighting(initialRanger,initialLocation,initialAnimalId);
        sightingDao.add(sighting);
        sightingDao.update(sighting.getId(),"vic","mountain",3);
        Sighting updatedSighting = sightingDao.findById(sighting.getId());
        assertNotEquals(initialRanger, updatedSighting.getRanger());
    }

    @Test
    public void deleteByIdDeletesCorrectEndangered() throws Exception {
        Sighting sighting = setupNewSighting();
        sightingDao.add(sighting);
        sightingDao.deleteById(sighting.getId());
        assertEquals(0, sightingDao.getAll().size());
    }

    @Test
    public void clearAllClearsAllEndangered() throws Exception {
        Sighting sighting = setupNewSighting();
        Sighting otherSighting = new Sighting("grace","river",1);
        sightingDao.add(sighting);
        sightingDao.add(otherSighting);
        int daoSize = sightingDao.getAll().size();
        sightingDao.clearAllSightings();
        assertTrue(daoSize > 0 && daoSize > sightingDao.getAll().size());
    }



    @Test
    void NewSightObjectGetsCorrectlyCreated_true() throws Exception {
        Sighting sighting = setupNewSighting();
        assertEquals(true,
                sighting instanceof Sighting);
    }


    public Sighting setupNewSighting(){
        return new Sighting("lilian","river",2);
}
}