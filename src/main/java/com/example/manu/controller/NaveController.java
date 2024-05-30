package com.example.manu.controller;

import com.example.manu.Service.NaveService;
import com.example.manu.model.Nave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/naves")
public class NaveController {

    @Autowired
    private NaveService naveService;

    @GetMapping("/getAll")
    public List<Nave> getAllNaves(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Nave> pagedList = naveService.getNaves(pageable);
        return pagedList.toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nave> getNaveById(@PathVariable Long id){
        return naveService.getNavebyId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByName")
    public List<Nave> findByName(@RequestParam String name){
        return naveService.getByName(name);
    }

    @PostMapping("/createNave")
    public Nave updateNave(@RequestBody Nave nave){
        return naveService.createNave(nave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nave> updateNave(@PathVariable Long id, @RequestBody Nave nave){
        try {
            return ResponseEntity.ok(naveService.updateNave(id,nave));
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNave(@PathVariable Long id){
        try {
            naveService.deleteNave(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
