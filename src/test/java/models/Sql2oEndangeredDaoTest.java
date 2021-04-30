package models;

import org.junit.jupiter.api.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import static org.junit.jupiter.api.Assertions.*;

class Sql2oEndangeredDaoTest {
    private static Sql2oAnimalDao animalDao; //these variables are now static.
    private static Sql2oEndangeredDao endangeredDao;
    private static Connection conn; //these variables are now static.

    @BeforeAll //changed to @BeforeClass (run once before running any tests in this file)
    public static void setUp() throws Exception { //changed to static
        String connectionString = "jdbc:postgresql://localhost:5432/wildlifetracker_test"; // connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "sherry", "password"); // changed user and pass to null
        animalDao = new Sql2oAnimalDao(sql2o);
        endangeredDao = new Sql2oEndangeredDao(sql2o);
        conn = sql2o.open(); // open connection once before this test file is run
    }

    @AfterEach // run after every test
    public void tearDown() throws Exception { //I have changed
        System.out.println("clearing database");
        animalDao.clearAllAnimals(); // clear all tasks after every test
        endangeredDao.clearAllEndangered();
    }

    @AfterAll // changed to @AfterClass (run once after all tests in this file completed)
    public static void shutDown() throws Exception { //changed to static and shutDown
        conn.close(); // close connection once after this entire test file is finished
        System.out.println("connection closed");
    }
    @Test
    public void addingEndangeredSetsId() throws Exception {
        Endangered endangered = setupNewEndangered();
        int originalEndangeredId = endangered.getId();
        endangeredDao.add(endangered);
        assertNotEquals(originalEndangeredId, endangered.getId());
    }

    @Test
    public void existingEndangeredCanBeFoundById() throws Exception {
        Endangered endangered = setupNewEndangered();
        endangeredDao.add(endangered);
        Endangered foundEndangered = endangeredDao.findById(endangered.getId());
        assertEquals(endangered, foundEndangered);
    }

    @Test
    public void addedEndangeredAreReturnedFromGetAll() throws Exception {
        Endangered endangered = setupNewEndangered();
        endangeredDao.add(endangered);
        assertEquals(1, endangeredDao.getAll().size());
    }

    @Test
    public void noEndangeredReturnsEmptyList() throws Exception {
        assertEquals(0, endangeredDao.getAll().size());
    }

    @Test
    public void updateChangesEndangeredContent() throws Exception {
        String initialName = "dog";
        String initialHealth = "bad";
        String initialAge = "young";
        Endangered endangered = new Endangered (initialName,initialHealth,initialAge);
        endangeredDao.add(endangered);
        endangeredDao.update(endangered.getId(),"cheetah","good","young");
        Endangered updatedEndangered = endangeredDao.findById(endangered.getId());
        assertNotEquals(initialName, updatedEndangered.getName());
    }

    @Test
    public void deleteByIdDeletesCorrectEndangered() throws Exception {
        Endangered endangered = setupNewEndangered();
        endangeredDao.add(endangered);
        endangeredDao.deleteById(endangered.getId());
        assertEquals(0, endangeredDao.getAll().size());
    }

    @Test
    public void clearAllClearsAllEndangered() throws Exception {
        Endangered endangered = setupNewEndangered();
        Endangered otherEndangered = new Endangered("dog","good","young");
        endangeredDao.add(endangered);
        endangeredDao.add(otherEndangered);
        int daoSize = endangeredDao.getAll().size();
        endangeredDao.clearAllEndangered();
        assertTrue(daoSize > 0 && daoSize > endangeredDao.getAll().size());
    }



    // helper method
    public Endangered setupNewEndangered(){
        return new Endangered("dog","good","young");
    }
}