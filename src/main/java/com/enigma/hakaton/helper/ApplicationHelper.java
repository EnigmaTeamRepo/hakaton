package com.enigma.hakaton.helper;

import com.enigma.hakaton.model.User;
import com.enigma.hakaton.model.enums.UserStatus;
import com.enigma.hakaton.service.UserService;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Method;
import java.util.Date;

public class ApplicationHelper {
    public static void createDefaultAdmin(ConfigurableApplicationContext context) {
        UserService userService = context.getBean(UserService.class);

        try {
            Method registerDefaultAdmin = UserService.class.getDeclaredMethod("registerDefaultAdmin");
            registerDefaultAdmin.setAccessible(true);
            registerDefaultAdmin.invoke(userService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUserInformation(User user) {
        user.setName("Иван");
        user.setPatronymic("Иванович");
        user.setLastName("Иванов");
        user.setRegistrationDate(new Date());
        user.setBirthDate("01.01.1990");
        user.setUserStatus(UserStatus.NEW);
    }
}
