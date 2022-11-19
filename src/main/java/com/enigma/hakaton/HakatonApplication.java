package com.enigma.hakaton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
//@EnableJpaRepositories
public class HakatonApplication {

	public static void main(String[] args) {
		SpringApplication.run(HakatonApplication.class, args);
	}
}
