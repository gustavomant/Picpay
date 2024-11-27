package com.picpay.picpay.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.picpay.picpay.models.Role;
import com.picpay.picpay.models.RoleName;
import com.picpay.picpay.models.User;
import com.picpay.picpay.repositories.RoleRepository;
import com.picpay.picpay.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Configuration
public class UserSetup {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SecurityConfig securityConfig;

    @Bean
    @Order(2)
    CommandLineRunner initUsers() {
        return args -> {
            Role role = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new EntityNotFoundException());

            if(userRepository.findByName("John Doe").isEmpty()) {
                User user = new User(
                    0, 
                    "John Doe",
                    "12345678900",
                    "johndoe@email.com",
                    Set.of(role),
                    securityConfig.passwordEncoder().encode("password")
                );
                userRepository.save(user);
            }
        };
    }
}
