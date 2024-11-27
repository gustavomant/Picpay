package com.picpay.picpay.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpay.picpay.client.ExternalApiClient;
import com.picpay.picpay.dtos.TransferDto;
import com.picpay.picpay.exceptions.BusinessException;
import com.picpay.picpay.models.Role;
import com.picpay.picpay.models.RoleName;
import com.picpay.picpay.models.Transfer;
import com.picpay.picpay.models.User;
import com.picpay.picpay.repositories.RoleRepository;
import com.picpay.picpay.repositories.TransferRepository;
import com.picpay.picpay.repositories.UserRepository;

@Service
public class TransferService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExternalApiClient externalApiClient;

    public TransferDto createTransfer(
        Long payerId,
        Long payeeId,
        BigDecimal amount
    ) {
        externalApiClient.authorizeTransfer();
        Transfer transfer = new Transfer();
        User payer = userRepository.findById(payerId).orElseThrow();
        Optional<Role> role = roleRepository.findByName(RoleName.ROLE_SHOPKEEPER);
        if (role.isPresent() && payer.getRoles().contains(role.get())) {
            throw new BusinessException("The payer cannot have the role of a shopkeeper.");
        }
        User payee = userRepository.findById(payeeId).orElseThrow();
        transfer.setPayer(payer);
        transfer.setPayee(payee);
        transfer.setAmount(amount);
        transfer.setTimestamp(LocalDateTime.now());
        Transfer transfer2 = transferRepository.save(transfer);
        return new TransferDto(transfer2.getId(), transfer2.getPayer().getId(), transfer2.getPayee().getId());
    }
}
