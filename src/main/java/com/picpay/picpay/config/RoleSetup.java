package com.picpay.picpay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.picpay.picpay.models.Role;
import com.picpay.picpay.models.RoleName;
import com.picpay.picpay.repositories.RoleRepository;

@Configuration
public class RoleSetup {
    @Autowired
    private RoleRepository roleRepository;

    @Bean
    @Order(1)
    CommandLineRunner initRoles() {
        return args -> {
            if(roleRepository.findByName(RoleName.ROLE_USER).isEmpty()) {
                Role role = new Role();
                role.setName(RoleName.ROLE_USER);
                roleRepository.save(role);
            }

            if(roleRepository.findByName(RoleName.ROLE_SHOPKEEPER).isEmpty()) {
                Role role = new Role();
                role.setName(RoleName.ROLE_SHOPKEEPER);
                roleRepository.save(role);
            }
        };
    }
}
