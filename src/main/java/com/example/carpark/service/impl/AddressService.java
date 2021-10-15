package com.example.carpark.service.impl;

import com.example.carpark.entity.Address;
import com.example.carpark.repository.AddressRepository;
import com.example.carpark.service.BaseService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@AllArgsConstructor
@Transactional
public class AddressService implements BaseService<Address> {
    private final AddressRepository addressRepository;

    @Override
    public Collection<Address> getAll() {
        return this.addressRepository.findAll();
    }

    @Override
    public Address create(Address seedDto) {
        return this.addressRepository.save(seedDto);
    }

    @Override
    public Address findById(String id) throws NotFoundException {
        return this.addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Car not found!"));
    }

    @Override
    public boolean remove(String id) {
        return this.addressRepository.findAll().remove(id);
    }

    @Override
    public Address update(String id, Address viewDto) {
        return null;
    }

    @Override
    public Address getByName(String neighbourhood) {
        return this.addressRepository.findByNeighbourhood(neighbourhood);
    }
}
