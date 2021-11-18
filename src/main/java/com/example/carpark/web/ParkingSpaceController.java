package com.example.carpark.web;

import com.example.carpark.dto.AddressDTO;
import com.example.carpark.dto.ParkingSpaceDTO;
import com.example.carpark.entity.Address;
import com.example.carpark.service.impl.AddressService;
import com.example.carpark.service.impl.ParkingSpaceService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/parking/spot")
@AllArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;
    private final AddressService addressService;

    @GetMapping
    public String showParkingPlaces(Model model) {
        List<AddressDTO> addressDTOS = addressService.getAllDTOs();

        model.addAttribute("addresses", addressDTOS);

        return "choose-town";
    }

    @GetMapping("/sofia")
    public String showInSofia(Model model) {
        List<Address> sofiaAddresses = addressService.findAllFreeSpotsFor("Sofia");

        model.addAttribute("addresses", sofiaAddresses);

        return "sofia";
    }
    @GetMapping("/plovdiv")
    public String showInPlovdiv(Model model) {
        List<Address> sofiaAddresses = addressService.findAllFreeSpotsFor("Plovdiv");

        model.addAttribute("addresses", sofiaAddresses);

        return "plovdiv";
    }
    @GetMapping("/{townName}")
    public String showFreePlacesForTown(Model model, @PathVariable String townName) {
        List<Address> sofiaAddresses = addressService.findAllFreeSpotsFor(townName);

        model.addAttribute("addresses", sofiaAddresses);

        return "burgas";
    }
}
