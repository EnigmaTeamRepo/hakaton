package com.enigma.hakaton.service;

import com.enigma.hakaton.exception.LoginAlreadyExistException;
import com.enigma.hakaton.exception.PasswordNonMatchException;
import com.enigma.hakaton.helper.ApplicationHelper;
import com.enigma.hakaton.model.Bill;
import com.enigma.hakaton.model.User;
import com.enigma.hakaton.model.UserInfo;
import com.enigma.hakaton.model.dto.BillDTO;
import com.enigma.hakaton.model.dto.UserDTO;
import com.enigma.hakaton.model.enums.Currency;
import com.enigma.hakaton.model.enums.Role;
import com.enigma.hakaton.model.enums.UserStatus;
import com.enigma.hakaton.model.request.RegisterRequest;
import com.enigma.hakaton.model.request.UserIdJsonRequest;
import com.enigma.hakaton.repository.BillRepository;
import com.enigma.hakaton.repository.UserInfoRepository;
import com.enigma.hakaton.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class UserService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger("com.enigma.hakaton.service");

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserInfoRepository userInfoRepository;
    private final BillRepository billRepository;

    @Value("${spring.admin.login:admin}")
    private String adminLogin;
    @Value("${spring.admin.pass:admin}")
    private String adminPass;


    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserInfoRepository userInfoRepository, BillRepository billRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userInfoRepository = userInfoRepository;
        this.billRepository = billRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not exist");
        }
        user.getAuthorities().add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        return user;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAllByRole(Role.USER)
                .stream()
                .map(this::toUserDto)
                // не нужна администратору, информация о счетах пользователя
                .peek(dto -> dto.setBills(new HashSet<>()))
                .collect(toList());
    }

    public Boolean registerNew(RegisterRequest registerRequest) {
        if (userRepository.findByLogin(registerRequest.getLogin()) != null) {
            throw new LoginAlreadyExistException();
        }
        if (!Objects.equals(registerRequest.getPassword(), registerRequest.getPassword2())) {
            throw new PasswordNonMatchException();
        }
        User user = new User();
        user.setLogin(registerRequest.getLogin());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);
        user.setUserStatus(UserStatus.NEW);
        user.setRegistrationDate(new Date());
        userRepository.save(user);
        ApplicationHelper.addUserInformation(user);
        return user.getId() != null;
    }

    public Boolean approveUserRegistration(UserIdJsonRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        if (!user.getBills().isEmpty()) {
            throw new RuntimeException();
        }
        user.setUserStatus(UserStatus.ACTIVE);
        Bill bill = new Bill();
        bill.setBillAmount(0L);
        bill.setCurrency(Currency.RUB);
        bill.setBillNumber(UUID.randomUUID().toString());
        bill.setUser(user);
        user.getBills().add(bill);
        return userRepository.save(user).getUserStatus().equals(UserStatus.ACTIVE);
    }

    public Boolean blockUser(UserIdJsonRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        if (UserStatus.BLOCKED.equals(user.getUserStatus())) {
            return false;
        }
        user.setUserStatus(UserStatus.BLOCKED);
        return userRepository.save(user).getUserStatus().equals(UserStatus.BLOCKED);
    }

    public Boolean unblockUser(UserIdJsonRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        if (UserStatus.ACTIVE.equals(user.getUserStatus()) || UserStatus.NEW.equals(user.getUserStatus())) {
            return false;
        }
        user.setUserStatus(UserStatus.ACTIVE);
        return userRepository.save(user).getUserStatus().equals(UserStatus.ACTIVE);
    }

    public UserDTO getBtoByUserId(Long id) {
        return toUserDto(userRepository.findById(id).orElseThrow());
    }

    private UserDTO toUserDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setBills(user.getBills().stream().map(this::toBillDto).collect(toSet()));
        dto.setUserId(user.getId());
        dto.setUserStatus(user.getUserStatus());
        dto.setLogin(user.getLogin());
        dto.setRegistrationDate(user.getRegistrationDate());
        UserInfo userInfo = userInfoRepository.findById(user.getId()).orElse(null);
        if (userInfo != null) {
            dto.setName(userInfo.getName());
            dto.setPatronymic(userInfo.getPatronymic());
            dto.setBirthDate(userInfo.getBirthDate());
            dto.setLastName(userInfo.getLastName());
            dto.setGender(userInfo.getGender());
            dto.setPassportIssued(userInfo.getPassportIssued());
        }
        return dto;
    }

    private BillDTO toBillDto(Bill bill) {
        BillDTO dto = new BillDTO();
        dto.setId(bill.getId());
        dto.setBillAmount(bill.getBillAmount());
        dto.setBillNumber(bill.getBillNumber());
        dto.setCurrency(bill.getCurrency().getCode());
        return dto;
    }

    private void registerDefaultAdmin() {
        User defaultAdmin = new User();
        defaultAdmin.setLogin(adminLogin);
        defaultAdmin.setPassword(bCryptPasswordEncoder.encode(adminPass));
        defaultAdmin.setRole(Role.ADMIN);
        defaultAdmin.setUserStatus(UserStatus.ACTIVE);
        defaultAdmin.setRegistrationDate(new Date());
        userRepository.save(defaultAdmin);
    }
}
