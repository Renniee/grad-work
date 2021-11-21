package com.example.carpark.service.impl;

import com.example.carpark.dto.CarDTO;
import com.example.carpark.entity.Car;
import com.example.carpark.entity.UserEntity;
import com.example.carpark.repository.CarRepository;
import com.example.carpark.service.BaseService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CarService implements BaseService<CarDTO> {
    private final CarRepository carRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public Collection<CarDTO> getAll() {
        return this.carRepository.findAll()
                .stream()
                .map(c-> modelMapper.map(c,CarDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CarDTO create(CarDTO seedDTO) {
        Car car=modelMapper.map(seedDTO, Car.class);
        carRepository.save(car);
        CarDTO carDTO=modelMapper.map(car, CarDTO.class);
        return carDTO;
    }

    public Car create(Car car) {
        return carRepository.save(car);
    }

    @Transactional
    public Car addCarToUser(CarDTO seedDto) throws NotFoundException {
        Car car = this.modelMapper.map(seedDto, Car.class);
        car.setCreated(Instant.now());
        car.setModified(Instant.now());

        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal. toString();
        }

        UserEntity userDTO = userService.getByNameV1(username);
        car.setUser(modelMapper.map(userDTO, UserEntity.class));

//        userDTO.getCars().add(car);
        return this.carRepository.save(car);
    }

    @Override
    public CarDTO findById(String id) throws NotFoundException {
        return this.carRepository.findById(id)
                .map(c -> modelMapper.map(c, CarDTO.class))
                .orElseThrow(() -> new NotFoundException("Car not found!"));
    }

    @Override
    public boolean remove(String id) {
        return this.carRepository.findAll().remove(id);
    }

    @Override
    public CarDTO update(String id, CarDTO viewDto){
        return null;
    }

    @Override
    public CarDTO getByName(String registrationNumber) {
        Car carByRegistrationNumber=carRepository.findByRegistrationNumber(registrationNumber);
        return modelMapper.map(carByRegistrationNumber, CarDTO.class);
    }
}
