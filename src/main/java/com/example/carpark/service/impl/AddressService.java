package com.example.carpark.service.impl;

import com.example.carpark.dto.AddressDTO;
import com.example.carpark.dto.AddressViewDTO;
import com.example.carpark.dto.ParkingSpaceDTO;
import com.example.carpark.entity.Address;
import com.example.carpark.repository.AddressRepository;
import com.example.carpark.service.BaseService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AddressService implements BaseService<Address> {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

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
                .orElseThrow(() -> new NotFoundException("Address not found!"));
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

    public List<AddressDTO> getAllDTOs() {

        return this.addressRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, AddressDTO.class))
                .collect(Collectors.toList());

    }

    public List<Address> findAllFreeSpotsFor(String city) {
        return addressRepository.findAll()
                .stream()
                .filter(address -> address.getCity().equalsIgnoreCase(city))
//                .map(a -> modelMapper.map(a, AddressDTO.class))
                .collect(Collectors.toList());
    }


    public AddressViewDTO findByAddressId(String id) throws NotFoundException {
        return this.addressRepository.findById(id)
                .map(a -> modelMapper.map(a, AddressViewDTO.class))
                .orElseThrow(() -> new NotFoundException("Address not found!"));
    }
}
