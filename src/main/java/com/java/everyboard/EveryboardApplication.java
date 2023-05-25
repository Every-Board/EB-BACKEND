package com.java.everyboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EveryboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(EveryboardApplication.class, args);
	}

}
