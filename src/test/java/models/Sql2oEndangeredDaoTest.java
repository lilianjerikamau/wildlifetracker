package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import static org.junit.jupiter.api.Assertions.*;

class Sql2oEndangeredDaoTest {
    private Sql2oEndangeredDao endangeredDao;
    private Sql2oAnimalDao animalDao;
    private Connection conn;
    @BeforeEach
    void setUp() throws Exception {
            String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
            Sql2o sql2o = new Sql2o(connectionString, "", "");
            endangeredDao = new Sql2oEndangeredDao(sql2o);
            animalDao = new Sql2oAnimalDao(sql2o);
            conn = sql2o.open();
        }


    @AfterEach
    void tearDown() throws Exception {
        conn.close();
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
        int initialAge = 2;
        Endangered endangered = new Endangered (initialName,initialHealth,initialAge);
        endangeredDao.add(endangered);
        endangeredDao.update(endangered.getId(),"cheetah","good",3);
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
        Endangered otherEndangered = new Endangered("dog","good",1);
        endangeredDao.add(endangered);
        endangeredDao.add(otherEndangered);
        int daoSize = endangeredDao.getAll().size();
        endangeredDao.clearAllEndangered();
        assertTrue(daoSize > 0 && daoSize > endangeredDao.getAll().size());
    }

    @Test
    public void getAllAnimalsByEndangeredReturnsAnimalsCorrectly() throws Exception {
        Endangered endangered = setupNewEndangered();
        endangeredDao.add(endangered);
        int endangeredId = endangered.getId();
        Animal newAnimal = new Animal("cheetah", endangeredId);
        Animal otherAnimal = new Animal("dog", endangeredId);
        Animal thirdAnimal = new Animal("cat", endangeredId);
        animalDao.add(newAnimal);
        animalDao.add(otherAnimal); //we are not adding task 3 so we can test things precisely.
        assertEquals(2, endangeredDao.getAllAnimalsByCategory(endangeredId).size());
        assertTrue(endangeredDao.getAllAnimalsByCategory(endangeredId).contains(newAnimal));
        assertTrue(endangeredDao.getAllAnimalsByCategory(endangeredId).contains(otherAnimal));
        assertFalse(endangeredDao.getAllAnimalsByCategory(endangeredId).contains(thirdAnimal)); //things are accurate!
    }

    // helper method
    public Endangered setupNewEndangered(){
        return new Endangered("dog","good",2);
    }
}