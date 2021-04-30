package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EndangeredTest {


    @Test
    void getName() {
        Endangered endangered = setupNewEndangered();
        assertEquals("cheetah",endangered.getName());
    }
    @Test
    void NewEndangeredObjectGetsCorrectlyCreated_true() throws Exception {
        Endangered animal = setupNewEndangered();
        assertEquals(true,
                animal instanceof Endangered);
    }
    @Test
    void getAge() {
        Endangered endangered = setupNewEndangered();
        assertEquals("young",endangered.getAge());
    }



    @Test
    void getHealth() {
        Endangered endangered = setupNewEndangered();
        assertEquals("good",endangered.getHealth());
    }


    public Endangered setupNewEndangered(){
        return new Endangered("cheetah","good","young");
    }
}
