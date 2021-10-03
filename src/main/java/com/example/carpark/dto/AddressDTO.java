package com.example.carpark.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressDTO {
    private String neighbourhood;
    private String street;
    private int numberOfStreet;
}
