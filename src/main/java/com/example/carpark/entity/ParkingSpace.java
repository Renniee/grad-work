package com.example.carpark.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "parking_spaces")
@Data
@NoArgsConstructor
public class ParkingSpace extends BaseEntity {

    @OneToOne
    private Address address;

    private BigDecimal price;

    @Column(name = "is_occupied")
    private boolean isOccupied;

    @OneToOne
    private Ticket ticket;
}
