package com.picpay.picpay.dtos;

public record TransferDto(
    Long id,
    Long payerId,
    Long payeeId 
) {}
