package com.example.carpark.service.impl;

import com.example.carpark.entity.RoleEntity;
import com.example.carpark.entity.RoleEnumType;
import com.example.carpark.entity.UserEntity;
import com.example.carpark.model.CurrentUser;
import com.example.carpark.repository.UserRepository;
import com.example.carpark.service.BaseService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements BaseService<UserEntity> {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Override
    public Collection<UserEntity> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public UserEntity create(UserEntity model) {
        model.setCreated(Instant.now());
        model.setModified(Instant.now());
        String encode = passwordEncoder.encode(model.getPassword());
        model.setPassword(encode);
        return this.userRepository.save(model);
    }

    @Override
    public UserEntity findById(String id) throws NotFoundException {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public boolean remove(String id) {
        return userRepository.findAll().remove(id);
    }

    @Override
    public UserEntity update(String id, UserEntity user) {
        user.setModified(Instant.now());
        return userRepository.save(user);
    }

    @Override
    public UserEntity getByName(String name) throws NotFoundException {
        return this.userRepository.findByUsername(name)
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }

    public boolean isUserExists(String username) {
        UserEntity userEntity = this.userRepository.findByUsername(username).orElse(null);
        return userEntity != null;
    }

    public boolean isLoginValid(String userName, String password) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(userName);

        return userEntity.
                map(UserEntity::getPassword).
                filter(pwd -> passwordEncoder.matches(password, pwd)).
                isPresent();
    }

    public void loginUser(String userName) {

        UserEntity userEntity = userRepository.
                findByUsername(userName).
                orElseThrow(() -> new IllegalArgumentException("User with name " + userName + " not found!"));

        List<RoleEnumType> userRoles = userEntity.
                getRoles().
                stream().
                map(RoleEntity::getRole).
                collect(Collectors.toList());

        currentUser.setName(userEntity.getUsername());
        currentUser.addUserRoles(userRoles);
        currentUser.setAnonymous(false);
    }
}

