package com.example.manu.repository;


import com.example.manu.model.Nave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NaveRepository extends JpaRepository<Nave, Long> {

    @Query("SELECT n From Nave n WHERE n.name LIKE %:name%")
    List<Nave> findByName(@Param("name") String name);
}
