package com.example.carpark.service.impl;


import com.example.carpark.entity.UserEntity;
import com.example.carpark.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        LOGGER.debug("Trying to load user {}. Successful? {}", username, userOpt.isPresent());
        return userOpt.map(this::map).orElseThrow(() -> new UsernameNotFoundException("No such user " + username));
    }

    private User map(UserEntity userEntity) {
        List<GrantedAuthority> authorities = userEntity.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority("ADMIN"))
                .collect(Collectors.toList());

        User result = new User(userEntity.getUsername()
                , userEntity.getPassword() != null
                ? userEntity.getPassword()
                : "", authorities);

        if (userEntity.getPassword() == null) {
            result.eraseCredentials();
        }
        return result;
    }
}