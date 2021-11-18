package com.example.carpark.service.impl;

import com.example.carpark.dto.CarDTO;
import com.example.carpark.entity.Car;
import com.example.carpark.entity.UserEntity;
import com.example.carpark.model.CurrentUser;
import com.example.carpark.repository.CarRepository;
import com.example.carpark.service.BaseService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@AllArgsConstructor
@Transactional
public class CarService implements BaseService<Car> {
    private final CarRepository carRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    @Override
    public Collection<Car> getAll() {
        return this.carRepository.findAll();
    }

    @Override
    public Car create(Car car) {
        car.setCreated(Instant.now());
        car.setModified(Instant.now());

        return this.carRepository.saveAndFlush(car);
    }

    @Transactional
    public Car addCarToUser(CarDTO seedDto) throws NotFoundException {
        Car car = this.modelMapper.map(seedDto, Car.class);
        car.setCreated(Instant.now());
        car.setModified(Instant.now());
        UserEntity user = null;
        try {
            user = userService.getByName(currentUser.getName());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        car.setUser(user);

        return this.carRepository.save(car);
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
