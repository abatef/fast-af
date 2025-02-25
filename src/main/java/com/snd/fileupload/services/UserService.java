package com.snd.fileupload.services;

import com.snd.fileupload.exceptions.UserNotFoundException;
import com.snd.fileupload.models.User;
import com.snd.fileupload.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findById(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public User crateUser(User user) {
        return userRepository.save(user);
    }

}
