package com.example.carpark.service.impl;

import com.example.carpark.dto.LoginDTO;
import com.example.carpark.dto.UserDTO;
import com.example.carpark.dto.UserDetailsServiceModel;
import com.example.carpark.entity.RoleEntity;
import com.example.carpark.entity.UserEntity;
import com.example.carpark.model.CurrentUser;
import com.example.carpark.repository.UserRepository;
import com.example.carpark.service.BaseService;
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
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements BaseService<UserEntity> {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;
    private final UserDetailsServiceImpl userDetailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Override
    public Collection<UserEntity> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public UserEntity create(UserEntity model) {
        model.setCreated(Instant.now());
        model.setModified(Instant.now());
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
    public UserEntity update(String id, UserEntity viewDto) {
        return null;
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

//    public boolean loginUser(String username, String password) {
//
//        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
//
//        if (user.isPresent()) {
//           return true;
//           // return passwordEncoder.matches(password, user.get().getPassword());
//        }
//        return false;
//    }


    public LoginDTO getUserByName(String username) throws NotFoundException {
        return this.userRepository.findByUsername(username)
                .map(u -> this.modelMapper.map(u, LoginDTO.class))
                .orElseThrow(() -> new NotFoundException("User with username {} not found"));

    }


    public void login(String username) {
        currentUser.setAnonymous(false);
        currentUser.setName(username);
    }

    public UserEntity getOrCreateUser(UserDTO userServiceModel) {
        Objects.requireNonNull(userServiceModel.getPassword());
        Optional<UserEntity> userEntityOpt = userRepository.findByUsername(userServiceModel.getUsername());
        return userEntityOpt.orElseGet(() -> createUser(userServiceModel));
    }

    public void createAndLoginUser(UserDTO userServiceModel) {
        UserEntity newUserEntity = createUser(userServiceModel);
        UserDetails userDetails = userDetailsService.loadUserByUsername(newUserEntity.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userServiceModel.getPassword(),
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void loginUser(String username, String password) throws NotFoundException {
        UserDetailsServiceModel userDetailsServiceModel = this.findUserByUsername(username);
//        if(!userDetailsServiceModel.getPassword().equals(passwordEncoder.encode(password))){
//            throw new PasswordNotCorrectException();
//        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }


    public UserDetailsServiceModel findUserByUsername(String username) throws NotFoundException {
        UserEntity userEntity = this.userRepository.findByUsername(username).orElse(null);
        if (userEntity != null) {

            UserDetailsServiceModel userDetailsServiceModel = this.modelMapper.map(userEntity, UserDetailsServiceModel.class);
            userDetailsServiceModel.setUsername(userEntity.getUsername());
            return userDetailsServiceModel;
        }
        throw new NotFoundException(username);
    }


    // userServiceModel = userDTO
    private UserEntity createUser(UserDTO userServiceModel) {
        UserEntity userEntity = new UserEntity();
        LOGGER.info("Creating a new user with username [GDPR].");
        userEntity = this.createUserWithRoles(userServiceModel, "USER");
        return userRepository.save(userEntity);
    }

    private UserEntity createUserWithRoles(UserDTO userServiceModel, String role) {
        UserEntity userEntity = this.modelMapper.map(userServiceModel, UserEntity.class);

        if (userServiceModel.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        }
        RoleEntity userRole = new RoleEntity();
        userRole.setRole(role);
        userEntity.setRoles(List.of(userRole));
        return userEntity;
    }
}

