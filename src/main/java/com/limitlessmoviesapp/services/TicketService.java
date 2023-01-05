package com.limitlessmovies.services;

import com.limitlessmovies.models.Ticket;
import com.limitlessmovies.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepo;

    public TicketService(TicketRepository ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public List<Ticket> getAllTickets(){
        return ticketRepo.findAll();
    }

    public Ticket getCinemaTickets(Long cinemaId){
        return ticketRepo.findByCinemaIdIs(cinemaId);
    }

    public Ticket getMovieTickets(Long movieId){
        return ticketRepo.findByMovieIdIs(movieId);
    }

    public void updateTicket(Ticket ticket) {
        ticketRepo.save(ticket);
    }

    public void deleteTicket(Ticket ticket) {
        ticketRepo.delete(ticket);
    }
}