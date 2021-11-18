package com.example.carpark.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "parking_spaces")
@Data
@NoArgsConstructor
public class ParkingSpace extends BaseEntity {

    @OneToOne
    private Address address;

    private BigDecimal price;

    @OneToOne
    private Ticket ticket;
}
