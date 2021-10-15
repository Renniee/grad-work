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
//
//        UserEntity userEntity = userService.getByName("admin");
//        initCar(userEntity, "CM2111AM");
//        UserEntity userEntity2 = userService.getByName("AzImEcA");
//        initCar(userEntity2, "CB11111");
//        UserEntity userEntity3 = userService.getByName("Kolio");
//        initCar(userEntity3, "PB1441");


        Address sofia = initAddress("Sofia", "Mladost", "Alexander Malinov", 4);
        Address sofia1 = initAddress("Sofia", "Obelya", "6-ta", 7);
        Address sofia2 = initAddress("Sofia", "Vitosha", "Narcissus", 9);
        Address plovdiv = initAddress("Plovdiv", "Kamenitsa", "Rodopi", 11);
        Address plovdiv1 = initAddress("Plovdiv", "Kichuk Parij", "Peter Vaskov", 6);
        Address plovdiv2 = initAddress("Plovdiv", "Smirnenski", "Peshtersko shose", 123);
        Address burgas = initAddress("Burgas", "Zornitsa", "Boycho Branzov", 1);;
        Address burgas1 = initAddress("Burgas", "Izgrev", "Petko Zadgorski", 12);;
        Address burgas2 = initAddress("Burgas", "Slaveykov", "Trakia", 3);;

        ParkingSpace parkingSpace = initParkingSpace(sofia, BigDecimal.valueOf(2.50));
        ParkingSpace parkingSpace1 = initParkingSpace(sofia1, BigDecimal.valueOf(2.50));
        ParkingSpace parkingSpace2 = initParkingSpace(sofia2, BigDecimal.valueOf(2.50));
        ParkingSpace parkingSpace11 = initParkingSpace(plovdiv, BigDecimal.valueOf(2.00));
        ParkingSpace parkingSpace12 = initParkingSpace(plovdiv1, BigDecimal.valueOf(2.00));
        ParkingSpace parkingSpace13 = initParkingSpace(plovdiv2, BigDecimal.valueOf(2.00));
        ParkingSpace parkingSpace21 = initParkingSpace(burgas, BigDecimal.valueOf(1.50));
        ParkingSpace parkingSpace22 = initParkingSpace(burgas1, BigDecimal.valueOf(1.50));
        ParkingSpace parkingSpace23 = initParkingSpace(burgas2, BigDecimal.valueOf(1.50));
    }

    private ParkingSpace initParkingSpace(Address address, BigDecimal price) {
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setAddress(address);
        parkingSpace.setPrice(price);

        setCurrentTimestamps(parkingSpace);
        parkingSpaceService.create(parkingSpace);

        return parkingSpace;
    }

    private Address initAddress(String city, String neighbourhood, String street, int numberOfStreet) {
        Address address = new Address();
        address.setCity(city);
        address.setNeighbourhood(neighbourhood);
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
        userRoleAdmin.setRole(RoleEnumType.ADMIN);
        RoleEntity userRoleUser = new RoleEntity();
        userRoleUser.setRole(RoleEnumType.USER);

        userRoleRepository.saveAll(List.of(userRoleAdmin, userRoleUser));
    }
}
