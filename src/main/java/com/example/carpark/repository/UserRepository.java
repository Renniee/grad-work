package com.example.carpark.repository;

import com.example.carpark.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
     Optional<UserEntity> findByUsernameAndPassword(String username, String password);

     Optional<UserEntity> findByUsername(String username);

}
