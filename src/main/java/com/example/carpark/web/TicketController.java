package com.example.carpark.web;

import com.example.carpark.dto.AddressViewDTO;
import com.example.carpark.service.impl.AddressService;
import com.example.carpark.service.impl.TicketService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ticket")
@AllArgsConstructor
public class TicketController {

    private final AddressService addressService;
    private final TicketService ticketService;

    @GetMapping("/buy")
    public String showTicket() {
        return "ticket-buy-successful";
    }

    @PostMapping("/buy")
    public String buyTicket(@ModelAttribute(value = "id") String id, Model model) {
        AddressViewDTO address = new AddressViewDTO();
        try {
            address = addressService.findByAddressId(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        model.addAttribute(address);

        return "ticket-buy-successful";
    }
}
