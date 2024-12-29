package com.example.kanbansystem.service;

import com.example.kanbansystem.Repository.UserRepository;
import com.example.kanbansystem.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Save a new user
     */
    public User saveUser(User userDto) {
        User new_user = new User(
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.isActive(),
                userDto.getRoles()
        );

        return userRepository.save(new_user);
    }

    /**
     * Fetch a user by ID
     */
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Fetch users by a list of IDs
     */
    public List<User> getUsersByIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }
}
