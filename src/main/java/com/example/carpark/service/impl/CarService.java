package com.example.carpark.service.impl;

import com.example.carpark.entity.Car;
import com.example.carpark.repository.CarRepository;
import com.example.carpark.service.BaseService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CarService implements BaseService<Car> {
    private CarRepository carRepository;

    @Override
    public Collection<Car> getAll() {
        return this.carRepository.findAll();
    }

    @Override
    public Car create(Car seedDto) {
        return this.carRepository.save(seedDto);
    }

    @Override
    public Car findById(String id) throws NotFoundException {
        return this.carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Car not found!"));
    }

    @Override
    public boolean remove(String id) {
        return this.carRepository.findAll().remove(id);
    }

    @Override
    public Car update(String id, Car viewDto) throws NotFoundException {
        return null;
    }

    @Override
    public Car getByName(String registrationNumber) {
        return this.carRepository.findByRegistrationNumber(registrationNumber);
    }
}
