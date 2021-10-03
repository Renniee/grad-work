package com.example.carpark.service.impl;

import com.example.carpark.entity.User;
import com.example.carpark.repository.UserRepository;
import com.example.carpark.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements BaseService<User> {

    private UserRepository userRepository;

    @Override
    public Collection<User> getAll() {
        return null;
    }

    @Override
    public User create(User seedDto) {
        return null;
    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public User update(String id, User viewDto) {
        return null;
    }
}
