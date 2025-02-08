package com.limitlessmoviesapp.service;

import com.limitlessmoviesapp.model.Cinema;
import com.limitlessmoviesapp.model.Movie;
import com.limitlessmoviesapp.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie getMovie(Long id) {
        return movieRepository.findByIdIs(id);
    }

    public void updateMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public void deleteMovie(Movie movie) {
        movieRepository.delete(movie);
    }

    public List<Movie> getAllMovies(String keyword) {
        if (keyword != null) {
            return movieRepository.search(keyword);
        }
        return movieRepository.findAll();
    }

    public List<Movie> getCinemaMovies(Cinema cinema) {
        List<Movie> movies = movieRepository.findAll();
        List<Movie> cinemaMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getCinemas().contains(cinema)) {
                cinemaMovies.add(movie);
            }
        }
        return cinemaMovies;
    }
}
