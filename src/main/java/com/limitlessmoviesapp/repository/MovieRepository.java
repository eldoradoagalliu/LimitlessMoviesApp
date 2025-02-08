package com.limitlessmoviesapp.repository;

import com.limitlessmoviesapp.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByIdIs(Long id);

    @Query("SELECT movie FROM Movie movie WHERE movie.title LIKE %?1% OR movie.genre LIKE %?1%")
    List<Movie> search(String keyword);
}
