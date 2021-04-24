package models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;


class AnimalTest {
    @Test
    void NewAnimalObjectGetsCorrectlyCreated_true() throws Exception {
        Animal animal = setupNewAnimal();
        assertEquals(true,
                animal instanceof Animal);
    }

    @Test
    public void AnimalInstantiatesWithName_true() throws Exception {
        Animal animal = setupNewAnimal();
        assertEquals("cheetah", animal.getName());
    }

    @Test
    public void isEndangeredPropertyIsFalseAfterInstantiation() throws Exception {
        Animal animal = setupNewAnimal();
        assertEquals(false, animal.getEndangered()); //should never start as completed
    }

    @Test
    public void getCreatedAtInstantiatesWithCurrentTimeToday() throws Exception {
        Animal animal = setupNewAnimal();
        assertEquals(LocalDateTime.now().getDayOfWeek(), animal.getCreatedAt().getDayOfWeek());
    }

    //helper methods
    public Animal setupNewAnimal(){
        return new Animal("cheetah", 1);
    }
}