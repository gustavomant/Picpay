package com.picpay.picpay.dtos;

public record CreateUserDto(
    String name,
    String cpf,
    String email,
    String password
){}
