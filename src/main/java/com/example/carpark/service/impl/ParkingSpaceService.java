package com.example.carpark.service.impl;

import com.example.carpark.dto.ParkingSpaceDTO;
import com.example.carpark.entity.ParkingSpace;
import com.example.carpark.repository.ParkingSpaceRepository;
import com.example.carpark.service.BaseService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ParkingSpaceService implements BaseService<ParkingSpaceDTO> {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ModelMapper modelMapper;

    @Override
    public Collection<ParkingSpaceDTO> getAll() {
        return this.parkingSpaceRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, ParkingSpaceDTO.class))
                .collect(Collectors.toList());
    }

    public List<ParkingSpaceDTO> getAllDTOs() {
        return this.parkingSpaceRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, ParkingSpaceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ParkingSpaceDTO create(ParkingSpaceDTO seedDTO) {
        ParkingSpace parkingSpace=modelMapper.map(seedDTO,ParkingSpace.class);
        parkingSpaceRepository.save(parkingSpace);
        ParkingSpaceDTO parkingSpaceDTO=modelMapper.map(parkingSpace, ParkingSpaceDTO.class);
        return parkingSpaceDTO;
    }

    public ParkingSpace create(ParkingSpace parkingSpace) {
        return parkingSpaceRepository.save(parkingSpace);
    }

    @Override
    public ParkingSpaceDTO findById(String id) throws NotFoundException {
        return this.parkingSpaceRepository.findById(id)
                .map(p -> modelMapper.map(p, ParkingSpaceDTO.class))
                .orElseThrow(()->new NotFoundException("Parking space not found!"));
    }

    @Override
    public boolean remove(String id) {
        return this.parkingSpaceRepository.findAll().remove(id);
    }

    @Override
    public ParkingSpaceDTO update(String id, ParkingSpaceDTO viewDto) throws NotFoundException {
        return null;
    }

    @Override
    public ParkingSpaceDTO getByName(String name) throws NotFoundException {
        return null;
    }

    public void findAddressByAddressId(String id) {

    }
}
