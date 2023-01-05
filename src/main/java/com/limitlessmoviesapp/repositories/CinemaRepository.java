package com.limitlessmovies.repositories;

import com.limitlessmovies.models.Cinema;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends CrudRepository<Cinema, Long> {
    List<Cinema> findAll();
    Cinema findByIdIs(Long id);
}