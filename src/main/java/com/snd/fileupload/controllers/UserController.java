package com.snd.fileupload.controllers;

import com.azure.core.annotation.Get;
import com.snd.fileupload.models.User;
import com.snd.fileupload.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.crateUser(user);
    }

    @GetMapping("/info")
    public User getUser(@RequestParam String username) {
        return userService.findByUsername(username);
    }
}
