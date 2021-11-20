package com.example.carpark.service.impl;

import com.example.carpark.dto.RegisterDTO;
import com.example.carpark.dto.UserDTO;
import com.example.carpark.entity.RoleEntity;
import com.example.carpark.entity.RoleEnumType;
import com.example.carpark.entity.UserEntity;
import com.example.carpark.repository.UserRepository;
import com.example.carpark.repository.UserRoleRepository;
import com.example.carpark.service.BaseService;
import com.example.carpark.service.CarParkUserDetailsService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
public class UserService implements BaseService<UserDTO> {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CarParkUserDetailsService carParkService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Override
    public Collection<UserDTO> getAll() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> modelMapper.map(u, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserEntity createV1(UserEntity model) {
        model.setCreated(Instant.now());
        model.setModified(Instant.now());
        String encode = passwordEncoder.encode(model.getPassword());
        model.setPassword(encode);
        return this.userRepository.save(model);
    }

    @Override
    public UserDTO create(UserDTO model) {
        UserEntity user = modelMapper.map(model, UserEntity.class);
        user.setCreated(Instant.now());
        user.setModified(Instant.now());
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        userRepository.save(user);
        return model;
    }

    @Override
    public UserDTO findById(String id) throws NotFoundException {
        return this.userRepository.findById(id)
                .map(u -> modelMapper.map(u, UserDTO.class))
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public boolean remove(String id) {
        return userRepository.findAll().remove(id);
    }

    @Override
    public UserDTO update(String id, UserDTO model) {
        return null;
    }

    @Override
    public UserDTO getByName(String name) throws NotFoundException {
        return this.userRepository.findByUsername(name)
                .map(u -> modelMapper.map(u, UserDTO.class))
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

//
//    public void loginUser(String userName) {
//
//        UserEntity userEntity = userRepository.
//                findByUsername(userName).
//                orElseThrow(() -> new IllegalArgumentException("User with name " + userName + " not found!"));
//
//        List<RoleEnumType> userRoles = userEntity.
//                getRoles().
//                stream().
//                map(RoleEntity::getRole).
//                collect(Collectors.toList());
//
//        currentUser.setName(userEntity.getUsername());
//        currentUser.addUserRoles(userRoles);
//        currentUser.setAnonymous(false);
//    }

    public void registerUser(RegisterDTO registerDTO) {

        RoleEntity userRole = userRoleRepository.findByRole(RoleEnumType.USER).orElse(null);

        UserEntity newUser = new UserEntity();

        newUser.setUsername(registerDTO.getUsername());
        newUser.setFirstName(registerDTO.getFirstName());
        newUser.setLastName(registerDTO.getLastName());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newUser.setRoles(List.of(userRole));
        newUser.setCreated(Instant.now());
        newUser.setModified(Instant.now());

        newUser = userRepository.save(newUser);

        UserDetails principal = carParkService.loadUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.
                getContext().
                setAuthentication(authentication);
    }

    public UserEntity getByNameV1(String name) {
        return userRepository.findByUsername(name).orElse(null);
    }
}

