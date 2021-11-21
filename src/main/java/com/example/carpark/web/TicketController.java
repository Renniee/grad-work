package com.example.carpark.web;

import com.example.carpark.dto.AddressDTO;
import com.example.carpark.dto.ParkingSpaceDTO;
import com.example.carpark.dto.TicketDTO;
import com.example.carpark.service.impl.AddressService;
import com.example.carpark.service.impl.ParkingSpaceService;
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
    private final ParkingSpaceService parkingSpaceService;
    private final TicketService ticketService;

    @GetMapping("/buy")
    public String showTicket() {
        return "ticket-buy-successful";
    }
    @GetMapping("/buy-error")
    public String addressOccupied() {
        return "buy-error";
    }

    @PostMapping("/buy")
    public String buyTicket(@ModelAttribute(value = "id") String id, Model model) {
        AddressDTO address = new AddressDTO();
        try {
            address = addressService.findByAddressId(id);
            address.setOccupied(true);
            addressService.update(id, address);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        model.addAttribute("address", address);

        return "ticket-buy-successful";
    }
}
