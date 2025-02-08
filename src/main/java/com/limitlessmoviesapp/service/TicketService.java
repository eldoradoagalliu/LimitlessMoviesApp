package com.limitlessmoviesapp.service;

import com.limitlessmoviesapp.model.Ticket;
import com.limitlessmoviesapp.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public void updateTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}
