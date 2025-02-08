package com.limitlessmoviesapp.service;

import com.limitlessmoviesapp.model.Cinema;
import com.limitlessmoviesapp.model.Movie;
import com.limitlessmoviesapp.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaService {

    private final CinemaRepository cinemaRepository;

    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    public Cinema getCinema(Long id) {
        return cinemaRepository.findByIdIs(id);
    }

    public List<Cinema> getMovieCinemas(Movie movie) {
        List<Cinema> cinemas = getAllCinemas();
        List<Cinema> movieCinemas = new ArrayList<>();
        for (Cinema cinema : cinemas) {
            if (cinema.getMovies().contains(movie)) {
                movieCinemas.add(cinema);
            }
        }
        return movieCinemas;
    }

    public void updateCinema(Cinema cinema) {
        cinemaRepository.save(cinema);
    }

    public void deleteCinema(Cinema cinema) {
        cinemaRepository.delete(cinema);
    }
}
