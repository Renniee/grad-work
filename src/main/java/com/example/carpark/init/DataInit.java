package com.example.carpark.init;

import com.example.carpark.service.impl.AddressService;
import com.example.carpark.service.impl.CarService;
import com.example.carpark.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInit implements CommandLineRunner {

    private final UserService userService;
    private final CarService carService;
    private final AddressService addressService;

    @Override
    public void run(String... args) throws Exception {
    }
}
