package com.example.carpark.web;

import com.example.carpark.dto.AddressViewDTO;
import com.example.carpark.entity.Address;
import com.example.carpark.service.impl.AddressService;
import com.example.carpark.service.impl.ParkingSpaceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/parking/spot")
@AllArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;
    private final AddressService addressService;

    @GetMapping
    public String showParkingPlaces(Model model) {
        List<String> cities = addressService.getAllDTOs();

        model.addAttribute("cities", cities);

        return "choose-town";
    }

    @GetMapping("/{townName}")
    public String showFreePlacesForTown(@PathVariable String townName, Model model) {
        List<Address> addresses = addressService.findAllFreeSpotsFor(townName);

        model.addAttribute("addresses", addresses);
        model.addAttribute("townName", townName);

        return "town";
    }
}
