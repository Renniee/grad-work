package com.example.carpark.service.impl;

import com.example.carpark.entity.Ticket;
import com.example.carpark.repository.TicketRepository;
import com.example.carpark.service.BaseService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
@AllArgsConstructor
public class TicketService implements BaseService<Ticket> {

    private final TicketRepository ticketRepository;

    @Override
    public Collection<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket create(Ticket model) {
        return ticketRepository.save(model);
    }

    @Override
    public Ticket findById(String id) throws NotFoundException {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found!"));
    }

    @Override
    public boolean remove(String id) {
        return this.ticketRepository.findAll().remove(id);
    }

    @Override
    public Ticket update(String id, Ticket viewDto) throws NotFoundException {
        return null;
    }

    @Override
    public Ticket getByName(String name) throws NotFoundException {
        return null;
    }
}
