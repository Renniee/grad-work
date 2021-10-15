package com.example.carpark.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
public class Ticket extends BaseEntity {


    @ManyToOne
    private UserEntity userEntity;

    @OneToOne
    private ParkingSpace parkingSpace;

    @OneToOne
    private Car car;

    @OneToOne
    private Code code;
}
