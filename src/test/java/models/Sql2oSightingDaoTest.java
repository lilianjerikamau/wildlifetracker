package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Sql2oSightingDaoTest {
    private Sql2oSightingDao sightingDao;
    private Sql2oAnimalDao animalDao;
    private Connection conn;

    @BeforeEach
    void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        sightingDao = new Sql2oSightingDao(sql2o);
        animalDao = new Sql2oAnimalDao(sql2o);
        conn = sql2o.open();
    }

    @AfterEach
    void tearDown() throws Exception {
        conn.close();
    }
    @Test
    public void addingSightingSetsId() throws Exception {
        Sighting sighting = setupNewSighting();
        int originalSightingId = sighting.getSightingId();
        sightingDao.add(sighting);
        assertNotEquals(originalSightingId, sighting.getSightingId());
    }
    @Test
    public void existingSightingCanBeFoundById() throws Exception {
        Sighting sighting = new Sighting("ken","beach",3);
        sightingDao.add(sighting);
        Sighting foundSighting = sightingDao.findById(sighting.getSightingId());
        assertEquals(sighting, foundSighting);
    }
    @Test
    public void existingSightingCanBeFoundByAnimalId() throws Exception {
        Sighting sighting = setupNewSighting();
        sightingDao.add(sighting);
        Sighting foundSighting = sightingDao.allByAnimal(sighting.getSightingId());
        assertEquals(sighting, foundSighting);
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
    public void updateChangesEndangeredContent() throws Exception {
        String initialRanger = "kamau";
        String initialLocation = "river";
        int initialAnimalId = 2;
        Sighting sighting = new Sighting(initialRanger,initialLocation,initialAnimalId);
        sightingDao.add(sighting);
        sightingDao.update(sighting.getSightingId(),"vic","mountain",3);
        Sighting updatedSighting = sightingDao.findById(sighting.getSightingId());
        assertNotEquals(initialRanger, updatedSighting.getRanger());
    }

    @Test
    public void deleteByIdDeletesCorrectEndangered() throws Exception {
        Sighting sighting = setupNewSighting();
        sightingDao.add(sighting);
        sightingDao.deleteById(sighting.getSightingId());
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