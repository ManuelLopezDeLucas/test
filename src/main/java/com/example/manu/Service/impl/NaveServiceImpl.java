package com.example.manu.Service.impl;

import com.example.manu.Service.NaveService;
import com.example.manu.model.Nave;
import com.example.manu.repository.NaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NaveServiceImpl implements NaveService {

    @Autowired
    private NaveRepository naveRepository;

    @Cacheable("naves")
    public Page<Nave> getNaves(Pageable pageable){
        return naveRepository.findAll(pageable);
    }

    @Cacheable("naves")
    public Optional<Nave> getNavebyId(Long id){
        return naveRepository.findById(id);
    }

    public List<Nave> getByName(String name){
        return naveRepository.findByName(name);
    }

    public Nave createNave(Nave nave){
        return naveRepository.save(nave);
    }

    public Nave updateNave(Long id, Nave nave) throws ChangeSetPersister.NotFoundException {
        Nave original = naveRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        original.setName(nave.getName());
        original.setSerie(nave.getSerie());
        naveRepository.save(nave);
        return original;
    }

    public void deleteNave(Long id) throws ChangeSetPersister.NotFoundException {
        Nave nave = naveRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        naveRepository.delete(nave);
    }
}
