package com.example.carpark.init;

import com.example.carpark.entity.*;
import com.example.carpark.repository.UserRoleRepository;
import com.example.carpark.service.impl.AddressService;
import com.example.carpark.service.impl.CarService;
import com.example.carpark.service.impl.ParkingSpaceService;
import com.example.carpark.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInit implements CommandLineRunner {

    private final UserService userService;
    private final CarService carService;
    private final AddressService addressService;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ParkingSpaceService parkingSpaceService;

    @Override
    public void run(String... args) throws Exception {
        initUsers();

        UserEntity userEntity = userService.getByName("admin");
        initCar(userEntity, "CM2111AM");
        UserEntity userEntity2 = userService.getByName("AzImEcA");
        initCar(userEntity2, "CB11111");

        Address sofia = initAddress("Sofia", "Mladost", "neshot", 4);
        //Address plovdiv = initAddress("Plovdiv");
        ParkingSpace parkingSpace = initParkingSpace(sofia, BigDecimal.valueOf(2.50));
    }

    private ParkingSpace initParkingSpace(Address address, BigDecimal price) {
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setAddress(address);
        parkingSpace.setPrice(price);

        setCurrentTimestamps(parkingSpace);
        parkingSpaceService.create(parkingSpace);

        return parkingSpace;
    }

    private Address initAddress(String city, String neighborhood, String street, int numberOfStreet) {
        Address address = new Address();
        address.setCity(city);
        address.setNeighborhood(neighborhood);
        address.setStreet(street);
        address.setNumberOfStreet(numberOfStreet);

        setCurrentTimestamps(address);
        addressService.create(address);

        return address;
    }

    private void initCar(UserEntity userEntity, String registrationNumber) {
        Car car = new Car();
        car.setRegistrationNumber(registrationNumber);
        car.setUser(userEntity);
        setCurrentTimestamps(car);
        carService.create(car);
    }

    private <T extends BaseEntity> void setCurrentTimestamps(T entity) {
        entity.setCreated(Instant.now());
        entity.setModified(Instant.now());
    }

    private void initUsers() {
        RoleEntity userRoleAdmin = new RoleEntity();
        userRoleAdmin.setRole("ADMIN");
        RoleEntity userRoleUser = new RoleEntity();
        userRoleUser.setRole("USER");

        userRoleRepository.saveAll(List.of(userRoleAdmin, userRoleUser));

        UserEntity admin = new UserEntity();
        admin.setFirstName("admin");
        admin.setLastName("adminov");
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123"));
        admin.setRoles(List.of(userRoleAdmin, userRoleUser));
        userService.create(admin);

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Azim");
        userEntity.setLastName("Azimov");
        userEntity.setUsername("AzImEcA");
        userEntity.setPassword(passwordEncoder.encode("123"));
        userEntity.setRoles(List.of(userRoleUser));
        userService.create(userEntity);


    }
}
