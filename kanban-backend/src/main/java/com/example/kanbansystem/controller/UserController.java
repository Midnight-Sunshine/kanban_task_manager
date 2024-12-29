package com.example.kanbansystem.controller;

import com.example.kanbansystem.Response.AuthenticationRequest;
import com.example.kanbansystem.Response.AuthenticationResponse;
import com.example.kanbansystem.entities.User;
import com.example.kanbansystem.security.MyuserDetailsService;
import com.example.kanbansystem.service.authService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private MyuserDetailsService myuserDetailsService;
    @Autowired
    public authService service;

    @PostMapping("/user")
    public String addUser(@RequestBody User user){
        myuserDetailsService.addNewUser(user);
        return "User added successfully!";
    }
    @GetMapping("/Allusers")
    public List<User> getAllUsers() {
        return myuserDetailsService.getAllUsers();
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = myuserDetailsService.findUserByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){
        System.out.println("inside controller");
        System.out.println(request);
        return ResponseEntity.ok(
                service.register(request)
        );
    }
}
