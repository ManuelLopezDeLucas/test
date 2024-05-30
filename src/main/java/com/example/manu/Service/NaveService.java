package com.example.manu.Service;

import com.example.manu.model.Nave;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface NaveService {

    Page<Nave> getNaves(Pageable pageable);
    Optional<Nave> getNavebyId(Long id);
    List<Nave> getByName(String name);
    Nave createNave(Nave nave);
    Nave updateNave(Long id, Nave nave) throws ChangeSetPersister.NotFoundException;
    void deleteNave(Long id) throws ChangeSetPersister.NotFoundException;
}
