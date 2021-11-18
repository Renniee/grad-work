package com.example.carpark.dto;

import com.example.carpark.entity.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class ParkingSpaceDTO {
    private BigDecimal price;
    private List<Address> addresses;
}
