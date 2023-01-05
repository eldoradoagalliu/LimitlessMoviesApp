package com.limitlessmovies.repositories;

import com.limitlessmovies.models.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findAll();
    Ticket findByCinemaIdIs(Long cinemaId);
    Ticket findByMovieIdIs(Long movieId);
}