package com.limitlessmovies.services;

import com.limitlessmovies.models.Cinema;
import com.limitlessmovies.models.Movie;
import com.limitlessmovies.repositories.CinemaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CinemaService {
    private final CinemaRepository cinemaRepo;

    public CinemaService(CinemaRepository cinemaRepo) {
        this.cinemaRepo = cinemaRepo;
    }

    public List<Cinema> getAllCinemas(){
        return cinemaRepo.findAll();
    }

    public Cinema getCinema(Long id){
        return cinemaRepo.findByIdIs(id);
    }

    public List<Cinema> getMovieCinemas(Movie movie){
       List<Cinema> cinemas = getAllCinemas();
       List<Cinema> movieCinemas = new ArrayList<>();
       for(Cinema cinema : cinemas){
          if(cinema.getMovies().contains(movie)){
              movieCinemas.add(cinema);
          }
       }
       return movieCinemas;
    }

    public void updateCinema(Cinema cinema) {
        cinemaRepo.save(cinema);
    }

    public void deleteCinema(Cinema cinema) {
        cinemaRepo.delete(cinema);
    }
}