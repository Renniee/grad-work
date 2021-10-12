package com.example.carpark.service.impl;

import com.example.carpark.entity.ParkingSpace;
import com.example.carpark.repository.ParkingSpaceRepository;
import com.example.carpark.service.BaseService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
@AllArgsConstructor
public class ParkingSpaceService implements BaseService<ParkingSpace> {

    private final ParkingSpaceRepository parkingSpaceRepository;

    @Override
    public Collection<ParkingSpace> getAll() {
        return null;
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
