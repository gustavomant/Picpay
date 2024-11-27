package com.picpay.picpay.dtos;

import java.math.BigDecimal;

public record CreateTransferDto(
    Long payerId,
    Long payeeId,
    BigDecimal amount
) {}
