package com.picpay.picpay.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.picpay.picpay.config.SecurityConfig;
import com.picpay.picpay.config.UserDetailsImpl;
import com.picpay.picpay.dtos.CreateUserDto;
import com.picpay.picpay.dtos.LoginUserDto;
import com.picpay.picpay.dtos.RecoveryJwtTokenDto;
import com.picpay.picpay.dtos.UserDto;
import com.picpay.picpay.models.Role;
import com.picpay.picpay.models.RoleName;
import com.picpay.picpay.models.User;
import com.picpay.picpay.repositories.RoleRepository;
import com.picpay.picpay.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private RoleRepository roleRepository;

    public List<UserDto> getAll() {
        List<UserDto> users = userRepository.findAll().stream().map(user -> {
            return new UserDto(user.getName(), user.getEmail());
        }).collect(Collectors.toList());

        return users;
    }

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());
        
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setName(createUserDto.name());
        user.setEmail(createUserDto.email());
        user.setCpf(createUserDto.cpf());
        user.setPassword(securityConfig.passwordEncoder().encode(createUserDto.password()));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new EntityNotFoundException());
        roles.add(userRole);
        user.setRoles(roles);
        user = userRepository.save(user);
    }
}
