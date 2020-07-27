package com.alex.springbaeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.alex.springbaeldung.persistence.repos")
@EntityScan("com.alex.springbaeldung.persistence.models")
@SpringBootApplication
public class SpringBaeldungApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBaeldungApplication.class, args);
	}

}
