package com.example.manu.model;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(NaveTest.class)
public class NaveTest {

    @Test
    public void testGettersAndSetters() {
        // Arrange
        Nave nave = new Nave();
        Long id = 1L;
        String name = "Falcon";
        String serie = "12345";

        // Act
        nave.setId(id);
        nave.setName(name);
        nave.setSerie(serie);

        // Assert
        assertEquals(id, nave.getId());
        assertEquals(name, nave.getName());
        assertEquals(serie, nave.getSerie());
    }

    @Test
    public void testNoArgsConstructor() {
        // Arrange
        Nave nave = new Nave();

        // Assert
        assertEquals(null, nave.getId());
        assertEquals(null, nave.getName());
        assertEquals(null, nave.getSerie());
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String name = "Falcon";
        String serie = "12345";

        // Act
        Nave nave = new Nave(id, name, serie);

        // Assert
        assertEquals(id, nave.getId());
        assertEquals(name, nave.getName());
        assertEquals(serie, nave.getSerie());
    }
}