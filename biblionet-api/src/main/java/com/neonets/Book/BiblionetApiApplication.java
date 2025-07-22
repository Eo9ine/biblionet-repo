package com.neonets.Book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BiblionetApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiblionetApiApplication.class, args);
	}

}
