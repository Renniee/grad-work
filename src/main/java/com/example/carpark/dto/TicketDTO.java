package com.example.carpark.dto;

import com.example.carpark.entity.Car;
import com.example.carpark.entity.Code;
import com.example.carpark.entity.ParkingSpace;
import com.example.carpark.entity.UserEntity;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class TicketDTO {
    // TODO 1.Get User
    private ParkingSpace parkingSpace;
    // TODO 2.Get CAR
    private Car car;
    private Code code;
}
