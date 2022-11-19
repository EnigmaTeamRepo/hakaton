package com.enigma.hakaton.service;

import com.enigma.hakaton.model.Bill;
import com.enigma.hakaton.model.Operation;
import com.enigma.hakaton.model.User;
import com.enigma.hakaton.model.dto.BillDTO;
import com.enigma.hakaton.model.dto.OperationDTO;
import com.enigma.hakaton.model.enums.Currency;
import com.enigma.hakaton.model.enums.OperationType;
import com.enigma.hakaton.repository.OperationRepository;
import com.enigma.hakaton.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class OperationService {

    private final Logger log = LoggerFactory.getLogger("com.enigma.hakaton.service");

    private final ValuteService valuteService;
    private final UserService userServicel;
    private final OperationRepository operationRepository;
    private final UserRepository userRepository;

    @Autowired
    public OperationService(ValuteService valuteService, UserService userServicel,
                            OperationRepository operationRepository, UserRepository userRepository) {
        this.valuteService = valuteService;
        this.userServicel = userServicel;
        this.operationRepository = operationRepository;
        this.userRepository = userRepository;
    }

    public Long buy(Integer code, Long lots, Long userId) {
        Long amount = valuteService.getAmount(Currency.getByCode(code), lots);
        Operation operation = new Operation();
        operation.setOperationType(OperationType.BUY);
        operation.setAmount(amount);
        Set<BillDTO> bills = userServicel.getBtoByUserId(userId).getBills();
        operation.setBillFrom(bills
                .stream()
                .filter(billDTO -> Currency.RUB.getCode().equals(billDTO.getCurrency()))
                .findFirst()
                .orElseThrow()
                .getId());
        operation.setBillTo(bills
                .stream()
                .filter(billDTO -> billDTO.getCurrency().equals(code))
                .findFirst()
                .orElseThrow()
                .getId());
        operation.setUserId(userId);
        operationRepository.save(operation);
        User user = userRepository.findById(userId).orElseThrow();
        Bill billTo = user.getBills().stream().filter(bill -> bill.getId().equals(operation.getBillTo())).findFirst().orElseThrow();
        Long billAmount = billTo.getBillAmount();
        BigDecimal result = new BigDecimal(billAmount != null ? billAmount : 0L).add(new BigDecimal(lots).multiply(new BigDecimal("100")));
        billTo.setBillAmount(result.longValue());
        userRepository.save(user);
        return amount;
    }

    public Long sell(Integer code, Long lots, Long userId) {
        Long amount = valuteService.getAmount(Currency.getByCode(code), lots);
        Operation operation = new Operation();
        operation.setOperationType(OperationType.SELL);
        operation.setAmount(amount);
        Set<BillDTO> bills = userServicel.getBtoByUserId(userId).getBills();
        operation.setBillFrom(bills
                .stream()
                .filter(billDTO -> billDTO.getCurrency().equals(code))
                .findFirst()
                .orElseThrow()
                .getId());
        operation.setBillTo(bills
                .stream()
                .filter(billDTO -> Currency.RUB.getCode().equals(billDTO.getCurrency()))
                .findFirst()
                .orElseThrow()
                .getId());
        operation.setUserId(userId);
        User user = userRepository.findById(userId).orElseThrow();
        Bill billFrom = user.getBills().stream().filter(bill -> bill.getId().equals(operation.getBillFrom())).findFirst().orElseThrow();
        Long billAmount = billFrom.getBillAmount();
        if (billAmount == null) {
            throw new RuntimeException();
        }
        BigDecimal result = new BigDecimal(billAmount).subtract(new BigDecimal(lots).multiply(new BigDecimal("100")));
        billFrom.setBillAmount(result.longValue());
        operationRepository.save(operation);
        userRepository.save(user);
        return amount;
    }

    public Long payOut(Long userId, Long amount) {
        Operation operation = new Operation();
        operation.setOperationType(OperationType.PAYOUT);
        operation.setAmount(amount);
        operation.setBillFrom(userServicel.getBtoByUserId(userId).getBills()
                .stream()
                .filter(billDTO -> Currency.RUB.getCode().equals(billDTO.getCurrency()))
                .findFirst()
                .orElseThrow()
                .getId());
        operation.setBillTo(null);
        operation.setUserId(userId);
        User user = userRepository.findById(userId).orElseThrow();
        Bill billFrom = user.getBills().stream().filter(bill -> bill.getId().equals(operation.getBillFrom())).findFirst().orElseThrow();
        Long billAmount = billFrom.getBillAmount();
        if (billAmount == null) {
            throw new RuntimeException();
        }
        BigDecimal result = new BigDecimal(billAmount).subtract(new BigDecimal(amount));
        billFrom.setBillAmount(result.longValue());
        operationRepository.save(operation);
        operationRepository.save(operation);
        return amount;
    }

    public Long payIn(Long userId, Long amount) {
        Operation operation = new Operation();
        operation.setOperationType(OperationType.SELL);
        operation.setAmount(amount);
        operation.setBillFrom(null);
        operation.setBillTo(userServicel.getBtoByUserId(userId).getBills()
                .stream()
                .filter(billDTO -> Currency.RUB.getCode().equals(billDTO.getCurrency()))
                .findFirst()
                .orElseThrow()
                .getId());
        operation.setUserId(userId);
        operationRepository.save(operation);
        User user = userRepository.findById(userId).orElseThrow();
        Bill billTo = user.getBills().stream().filter(bill -> bill.getId().equals(operation.getBillTo())).findFirst().orElseThrow();
        Long billAmount = billTo.getBillAmount();
        BigDecimal result = new BigDecimal(billAmount != null ? billAmount : 0L).add(new BigDecimal(amount));
        billTo.setBillAmount(result.longValue());
        userRepository.save(user);
        return amount;
    }

    public List<OperationDTO> getOperationDtoByUserId(Long userId) {
        return operationRepository.findByUserId(userId).stream().map(this::toOperationDto).collect(toList());
    }

    private OperationDTO toOperationDto(Operation operation) {
        OperationDTO dto = new OperationDTO();
        return dto;
    }
}
