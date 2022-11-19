package com.enigma.hakaton.helper;

import com.enigma.hakaton.model.User;
import com.enigma.hakaton.model.UserInfo;
import com.enigma.hakaton.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Method;

public class ApplicationHelper {

    private static final Logger log = LoggerFactory.getLogger("com.enigma.hakaton.helper");

    public static void createDefaultAdmin(ConfigurableApplicationContext context) {
        try {
            Method registerDefaultAdmin = UserService.class.getDeclaredMethod("registerDefaultAdmin");
            registerDefaultAdmin.setAccessible(true);
            registerDefaultAdmin.invoke(context.getBean(UserService.class));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * Имитирует запрос во внешнии сервисы, для сбора данных пользователя
     */
    public static void addUserInformation(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setName("Иван");
        userInfo.setPatronymic("Иванович");
        userInfo.setLastName("Иванов");
        userInfo.setBirthDate("01.01.1990");
        userInfo.setPassportSerial("4325545223");
        userInfo.setPassportIssued("Паспорт выдан: отделением УФМС России по Тюменской обл. в гор. Тюмени");
        userInfo.setBornPlace("Место рождения: гор. Пенза Пенсезнского Р-НА");
    }
}
