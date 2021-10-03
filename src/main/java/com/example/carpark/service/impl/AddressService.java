package com.example.carpark.service.impl;

import com.example.carpark.entity.Address;
import com.example.carpark.repository.AddressRepository;
import com.example.carpark.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@AllArgsConstructor
@Transactional
public class AddressService implements BaseService<Address> {
    private AddressRepository addressRepository;

    @Override
    public Collection<Address> getAll() {
        return null;
    }

    @Override
    public Address create(Address seedDto) {
        return null;
    }

    @Override
    public Address findById(String id) {
        return null;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public Address update(String id, Address viewDto) {
        return null;
    }
}
