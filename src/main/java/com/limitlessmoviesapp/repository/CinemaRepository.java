package com.limitlessmoviesapp.repository;

import com.limitlessmoviesapp.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Cinema findByIdIs(Long id);
}
