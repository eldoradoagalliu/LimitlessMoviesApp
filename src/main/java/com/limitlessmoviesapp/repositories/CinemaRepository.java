package com.limitlessmoviesapp.repositories;

import com.limitlessmoviesapp.models.Cinema;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends CrudRepository<Cinema, Long> {
    List<Cinema> findAll();
    Cinema findByIdIs(Long id);
}