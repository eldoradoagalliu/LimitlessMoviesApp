package com.limitlessmovies.services;

import com.limitlessmovies.models.Cinema;
import com.limitlessmovies.models.Movie;
import com.limitlessmovies.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepo;

    public MovieService(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    public Movie getMovie(Long id){
        return movieRepo.findByIdIs(id);
    }

    public void updateMovie(Movie movie) {
        movieRepo.save(movie);
    }

    public void deleteMovie(Movie movie) {
        movieRepo.delete(movie);
    }

    public List<Movie> getAllMovies(String keyword) {
        if (keyword != null) {
            return movieRepo.search(keyword);
        }
        return movieRepo.findAll();
    }

    public List<Movie> getCinemaMovies(Cinema cinema){
        List<Movie> movies = movieRepo.findAll();
        List<Movie> cinemaMovies = new ArrayList<>();
        for(Movie movie : movies){
            if(movie.getCinemas().contains(cinema)){
                cinemaMovies.add(movie);
            }
        }
        return cinemaMovies;
    }
}