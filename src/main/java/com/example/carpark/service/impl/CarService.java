package com.example.carpark.service.impl;

import com.example.carpark.entity.Car;
import com.example.carpark.repository.CarRepository;
import com.example.carpark.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@AllArgsConstructor
@Transactional
public class CarService implements BaseService<Car> {
    private CarRepository carRepository;

    @Override
    public Collection<Car> getAll() {
        return null;
    }

    @Override
    public Car create(Car seedDto) {
        return null;
    }

    @Override
    public Car findById(String id) {
        return null;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public Car update(String id, Car viewDto) {
        return null;
    }
}
