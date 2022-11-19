package com.enigma.hakaton.repository;

import com.enigma.hakaton.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByUserId(Long userId);
}
