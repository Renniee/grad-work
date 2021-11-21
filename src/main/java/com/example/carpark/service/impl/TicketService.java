package com.example.carpark.service.impl;

import com.example.carpark.dto.TicketDTO;
import com.example.carpark.entity.Ticket;
import com.example.carpark.repository.TicketRepository;
import com.example.carpark.service.BaseService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class TicketService implements BaseService<TicketDTO> {

    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;


    @Override
    public Collection<TicketDTO> getAll() {
        return this.ticketRepository.findAll()
                .stream()
                .map(t -> modelMapper.map(t, TicketDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TicketDTO create(TicketDTO seedDTO) {
        Ticket ticket = modelMapper.map(seedDTO, Ticket.class);
        ticketRepository.save(ticket);
        TicketDTO ticketDTO = modelMapper.map(ticket, TicketDTO.class);
        return ticketDTO;
    }

    @Override
    public TicketDTO findById(String id) throws NotFoundException {
        return this.ticketRepository.findById(id)
                .map(ticket -> modelMapper.map(ticket, TicketDTO.class))
                .orElseThrow(()->new NotFoundException("Ticket is not found!"));
    }

    @Override
    public boolean remove(String id) {
        return this.ticketRepository.findAll().remove(id);
    }

    @Override
    public TicketDTO update(String id, TicketDTO viewDto) throws NotFoundException {
        return null;
    }

    @Override
    public TicketDTO getByName(String name) {
//        Ticket ticketByNumber=ticketRepository.findById();
        return null;
    }
}
