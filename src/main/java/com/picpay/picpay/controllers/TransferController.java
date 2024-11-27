package com.picpay.picpay.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.picpay.picpay.dtos.CreateTransferDto;
import com.picpay.picpay.dtos.TransferDto;
import com.picpay.picpay.exceptions.BusinessException;
import com.picpay.picpay.exceptions.ExternalApiClientException;
import com.picpay.picpay.services.TransferService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<Object> createTransfer(@RequestBody CreateTransferDto createTransferDto) {
        try {
            TransferDto transferDto = transferService.createTransfer(createTransferDto.payerId(), createTransferDto.payeeId(), createTransferDto.amount());
            return ResponseEntity.status(HttpStatus.CREATED).body(transferDto);
        } catch (HttpClientErrorException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Authorization failed: Access Denied"));
        } catch (ExternalApiClientException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", exception.getMessage()));
        } catch (BusinessException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", exception.getMessage()));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", exception.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }  
    }
}
