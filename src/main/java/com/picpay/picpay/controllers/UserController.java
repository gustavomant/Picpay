package com.picpay.picpay.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picpay.picpay.dtos.CreateUserDto;
import com.picpay.picpay.dtos.LoginUserDto;
import com.picpay.picpay.dtos.RecoveryJwtTokenDto;
import com.picpay.picpay.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("token", token));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody CreateUserDto createUserDto) {
        try {
            userService.createUser(createUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .build();
        }
    }
    
}
