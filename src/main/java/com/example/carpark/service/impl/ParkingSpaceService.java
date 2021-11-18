package com.example.carpark.service.impl;

import com.example.carpark.dto.ParkingSpaceDTO;
import com.example.carpark.entity.ParkingSpace;
import com.example.carpark.repository.ParkingSpaceRepository;
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
@Transactional
@AllArgsConstructor
public class ParkingSpaceService implements BaseService<ParkingSpace> {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ModelMapper modelMapper;

    @Override
    public Collection<ParkingSpace> getAll() {
        return this.parkingSpaceRepository.findAll();
    }

    public List<ParkingSpaceDTO> getAllDTOs() {
        return this.parkingSpaceRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, ParkingSpaceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ParkingSpace create(ParkingSpace model) {
        return this.parkingSpaceRepository.save(model);
    }

    @Override
    public ParkingSpace findById(String id) throws NotFoundException {
        return null;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public ParkingSpace update(String id, ParkingSpace viewDto) throws NotFoundException {
        return null;
    }

    @Override
    public ParkingSpace getByName(String name) throws NotFoundException {
        return null;
    }
}
