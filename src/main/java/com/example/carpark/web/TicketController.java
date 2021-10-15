package com.example.carpark.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @GetMapping("/buy")
    public String showTicket() {

        return "ticket";
    }

    @PostMapping("/buy")
    public String buyTicket() {

        return "ticket";
    }
}
