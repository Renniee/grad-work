package com.example.carpark.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "codes")
@Data
@NoArgsConstructor
public class Code extends BaseEntity {

    private int number;
}
