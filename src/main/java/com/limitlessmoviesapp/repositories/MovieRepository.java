package com.limitlessmoviesapp.repositories;

import com.limitlessmoviesapp.models.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    List<Movie> findAll();
    Movie findByIdIs (Long id);

    @Query("SELECT movie FROM Movie movie WHERE movie.title LIKE %?1%"
            + " OR movie.genre LIKE %?1%")
    public List<Movie> search(String keyword);
}