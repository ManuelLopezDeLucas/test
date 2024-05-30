package com.example.manu.service;

import com.example.manu.Service.impl.NaveServiceImpl;
import com.example.manu.model.Nave;
import com.example.manu.repository.NaveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(NaveServiceTest.class)
public class NaveServiceTest {


    @Mock
    private NaveRepository naveRepository;

    @InjectMocks
    private NaveServiceImpl naveService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNaves() {
        Nave Nave1 = new Nave(1L, "X-Wing", "Star Wars");
        Nave Nave2 = new Nave(2L, "Enterprise", "Star Trek");
        Pageable pageable = PageRequest.of(0, 10);
        Page<Nave> navePage = new PageImpl<>(Arrays.asList(Nave1, Nave2), pageable, 2);

        when(naveRepository.findAll(pageable)).thenReturn(navePage);

        Page<Nave> ships = naveService.getNaves(pageable);
        assertNotNull(ships);
        assertEquals(2, ships.getTotalElements());
        verify(naveRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetNaveById() {
        Nave Nave = new Nave(1L, "X-Wing", "Star Wars");

        when(naveRepository.findById(1L)).thenReturn(Optional.of(Nave));

        Optional<Nave> foundNave = naveService.getNavebyId(1L);
        assertTrue(foundNave.isPresent());
        assertEquals("X-Wing", foundNave.get().getName());
        verify(naveRepository, times(1)).findById(1L);
    }

    @Test
    void testGetNavesByName() {
        Nave Nave = new Nave(1L, "X-Wing", "Star Wars");

        when(naveRepository.findByName("Wing")).thenReturn(Arrays.asList(Nave));

        List<Nave> Naves = naveService.getByName("Wing");
        assertNotNull(Naves);
        assertEquals(1, Naves.size());
        assertEquals("X-Wing", Naves.get(0).getName());
        verify(naveRepository, times(1)).findByName("Wing");
    }

    @Test
    void testCreateNave() {
        Nave Nave = new Nave(1L, "X-Wing", "Star Wars");

        when(naveRepository.save(Nave)).thenReturn(Nave);

        Nave createdNave = naveService.createNave(Nave);
        assertNotNull(createdNave);
        assertEquals("X-Wing", createdNave.getName());
        verify(naveRepository, times(1)).save(Nave);
    }

    @Test
    void testUpdateNave() throws ChangeSetPersister.NotFoundException {
        Nave existingNave = new Nave(1L, "X-Wing", "Star Wars");
        Nave newNaveDetails = new Nave(1L, "X-Wing Updated", "Star Wars Updated");

        when(naveRepository.findById(1L)).thenReturn(Optional.of(existingNave));
        when(naveRepository.save(existingNave)).thenReturn(existingNave);

        Nave updatedNave = naveService.updateNave(1L, newNaveDetails);
        assertNotNull(updatedNave);
        assertEquals("X-Wing Updated", updatedNave.getName());
        assertEquals("Star Wars Updated", updatedNave.getSerie());
        verify(naveRepository, times(1)).findById(1L);
        verify(naveRepository, times(1)).save(newNaveDetails);
    }

    @Test
    void testUpdateNaveNotFound() {
        Nave newNaveDetails = new Nave(1L, "X-Wing Updated", "Star Wars Updated");

        when(naveRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ChangeSetPersister.NotFoundException.class, () -> naveService.updateNave(1L, newNaveDetails));
        verify(naveRepository, times(1)).findById(1L);
        verify(naveRepository, times(0)).save(any(Nave.class));
    }

    @Test
    void testDeleteNave() throws ChangeSetPersister.NotFoundException {
        Nave Nave = new Nave(1L, "X-Wing", "Star Wars");

        when(naveRepository.findById(1L)).thenReturn(Optional.of(Nave));
        doNothing().when(naveRepository).delete(Nave);

        naveService.deleteNave(1L);
        verify(naveRepository, times(1)).findById(1L);
        verify(naveRepository, times(1)).delete(Nave);
    }

    @Test
    void testDeleteNaveNotFound() {
        when(naveRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ChangeSetPersister.NotFoundException.class, () -> naveService.deleteNave(1L));
        verify(naveRepository, times(1)).findById(1L);
        verify(naveRepository, times(0)).delete(any(Nave.class));
    }
}