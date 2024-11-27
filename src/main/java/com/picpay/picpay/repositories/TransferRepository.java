package com.picpay.picpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.picpay.picpay.models.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {}
