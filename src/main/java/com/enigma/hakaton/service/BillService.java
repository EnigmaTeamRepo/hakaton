package com.enigma.hakaton.service;

import com.enigma.hakaton.exception.BillAlreadyExistsException;
import com.enigma.hakaton.model.Bill;
import com.enigma.hakaton.model.User;
import com.enigma.hakaton.model.dto.BillDTO;
import com.enigma.hakaton.model.enums.Currency;
import com.enigma.hakaton.repository.BillRepository;
import com.enigma.hakaton.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class BillService {

    private final Logger log = LoggerFactory.getLogger("com.enigma.hakaton.service");

    private final UserService userService;
    private final UserRepository userRepository;

    public BillService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public Set<BillDTO> createNewBill(Long userId, Currency currency) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException();
        }
        BillDTO dto = userService.getBtoByUserId(userId).getBills()
                .stream()
                .filter(billDTO ->
                        currency.getCode().equals(billDTO.getCurrency()))
                .findAny().orElse(null);
        if (dto != null) {
            throw new BillAlreadyExistsException();
        }
        Bill bill = new Bill();
        bill.setBillAmount(0L);
        bill.setCurrency(currency);
        bill.setBillNumber(UUID.randomUUID().toString());
        bill.setUser(user);
        user.getBills().add(bill);
        userRepository.save(user);
        return userService.getBtoByUserId(userId).getBills();
    }
}
