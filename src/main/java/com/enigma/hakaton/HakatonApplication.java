package com.enigma.hakaton;

import com.enigma.hakaton.helper.ApplicationHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HakatonApplication {

	public static void main(String[] args) {
		ApplicationHelper.createDefaultAdmin(SpringApplication.run(HakatonApplication.class, args));
	}
}
