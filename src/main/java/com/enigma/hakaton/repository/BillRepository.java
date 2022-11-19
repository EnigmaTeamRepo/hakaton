package com.enigma.hakaton.repository;

import com.enigma.hakaton.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
