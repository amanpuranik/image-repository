package com.imagerepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<model,Long> {

    List<model> findAll();
    Optional<model>findByName(String name);

}
