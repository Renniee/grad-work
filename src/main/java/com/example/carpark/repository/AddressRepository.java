package com.example.carpark.repository;

import com.example.carpark.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

    Address findByNeighbourhood(String neighbourhood);
}
