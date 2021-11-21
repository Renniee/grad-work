package com.example.carpark.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@NoArgsConstructor
public class AddressDTO {
    private String id;
    private String city;
    private String neighbourhood;
    private String street;
    private int numberOfStreet;
    private boolean isOccupied;
    private int ticket = new Random().nextInt(999999);
}
