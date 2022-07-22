package com.librarymanagement.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.librarymanagement.errorhandlers.NotFoundException;
import com.librarymanagement.model.User;
import com.librarymanagement.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
    private ArrayList<User> userList;
    private String message = "";
    static final String ADMIN = "the.stha@gmail.com";

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        userList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userList.add(user);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable int userId) {
        if (userRepository.findById(userId) == null)
            return new ResponseEntity<>(("User not found with id" + userId), HttpStatus.OK);

        User user = userRepository.findById(userId).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        if (user.getFname() != null
                && user.getEmail() != null) {
            if (user.getEmail().equals(ADMIN))
                user.setRole("admin");
            else
                user.setRole("user");
            User _user = userRepository.save(user);
            return new ResponseEntity<>(_user, HttpStatus.OK);

        } else

            return new ResponseEntity<>("Validation Error", HttpStatus.OK);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable int userId, @RequestBody User user) {

        // if user not found
        if (userRepository.findById(userId) == null)
            return new ResponseEntity<>(("User not found with id" + userId), HttpStatus.OK);

        // if user exists
        User _user = userRepository.findById(userId).get();
        if (user.getFname() != null)
            _user.setFname(user.getFname());
        if (user.getLname() != null)
            _user.setLname(user.getLname());
        if (user.getEmail() != null)
            _user.setEmail(user.getEmail());
        if (user.getPassword() != null)
            _user.setPassword(user.getPassword());

        // && user.getEmail()!= null) {
        userRepository.save(_user);
        return new ResponseEntity<>(_user, HttpStatus.OK);

    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable int userId) {
        if (userRepository.findById(userId) == null)
            return new ResponseEntity<>(("User not found with id" + userId), HttpStatus.OK);

        userRepository.deleteById(userId);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    // public void setUserRole()

}
