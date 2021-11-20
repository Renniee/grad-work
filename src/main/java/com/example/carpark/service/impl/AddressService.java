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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AddressService implements BaseService<AddressDTO> {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    public Collection<AddressDTO> getAll() {
        return this.addressRepository.findAll()
                .stream()
                .map(a -> modelMapper.map(a, AddressDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO create(AddressDTO seedDto) {
        Address address = modelMapper.map(seedDto, Address.class);
        addressRepository.save(address);
        AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
        return addressDTO;
    }

    @Override
    public AddressDTO findById(String id) throws NotFoundException {
        return this.addressRepository.findById(id)
                .map(a -> modelMapper.map(a, AddressDTO.class))
                .orElseThrow(() -> new NotFoundException("Address not found!"));
    }

    @Override
    public boolean remove(String id) {
        return this.addressRepository.findAll().remove(id);
    }

    @Override
    public AddressDTO update(String id, AddressDTO viewDto) {
        return null;
    }

    @Override
    public AddressDTO getByName(String neighbourhood) {
        Address addressByNeighbourhood = addressRepository.findByNeighbourhood(neighbourhood);
        return modelMapper.map(addressByNeighbourhood, AddressDTO.class);
    }

    public List<String> getAllDTOs() {
        return this.addressRepository.findAll()
                .stream()
                .map(Address::getCity)
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

    public void createAddress(Address address) {
        addressRepository.save(address);
    }
}
