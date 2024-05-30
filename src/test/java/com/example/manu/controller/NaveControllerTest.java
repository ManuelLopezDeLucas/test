package com.example.manu.controller;


import com.example.manu.Service.NaveService;
import com.example.manu.model.Nave;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(NaveControllerTest.class)
public class NaveControllerTest {

    @Mock
    private NaveService naveService;

    @InjectMocks
    private NaveController naveController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllNaves() {
        Page<Nave> mockPage = mock(Page.class);
        List<Nave> naves = new ArrayList<>();
        naves.add(new Nave(1L, "Falcon", "12345"));
        when(mockPage.toList()).thenReturn(naves);
        when(naveService.getNaves(any(Pageable.class))).thenReturn(mockPage);

        List<Nave> result = naveController.getAllNaves(0, 10);

        assertEquals(naves, result);
    }

    @Test
    public void testGetNaveById() {
        Nave nave = new Nave(1L, "Falcon", "12345");
        when(naveService.getNavebyId(anyLong())).thenReturn(Optional.of(nave));

        ResponseEntity<Nave> response = naveController.getNaveById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(nave, response.getBody());
    }

    @Test
    public void testGetNaveById_NotFound() {
        when(naveService.getNavebyId(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Nave> response = naveController.getNaveById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null);
    }

    @Test
    public void testFindByName() {
        List<Nave> naves = new ArrayList<>();
        naves.add(new Nave(1L, "Falcon", "12345"));
        when(naveService.getByName(anyString())).thenReturn(naves);

        List<Nave> result = naveController.findByName("Falcon");

        assertEquals(naves, result);
    }

    @Test
    public void testUpdateNave() {
        Nave nave = new Nave(1L, "Falcon", "12345");
        when(naveService.createNave(any(Nave.class))).thenReturn(nave);

        Nave result = naveController.updateNave(nave);

        assertEquals(nave, result);
    }

    @Test
    public void testUpdateNave_NotFound() throws ChangeSetPersister.NotFoundException {
        Nave nave = new Nave(1L, "Falcon", "12345");
        when(naveService.updateNave(anyLong(), any(Nave.class))).thenThrow(ChangeSetPersister.NotFoundException.class);

        ResponseEntity<Nave> response = naveController.updateNave(1L, nave);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null);
    }

    @Test
    public void testDeleteNave() {
        Long id = 1L;

        ResponseEntity response = naveController.deleteNave(id);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }
}