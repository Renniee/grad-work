package com.example.carpark.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@NoArgsConstructor
public class AddressViewDTO {

    private String city;
    private String neighbourhood;
    private String street;
    private int numberOfStreet;
    private int ticketNumber = new Random().nextInt(999999);

}
