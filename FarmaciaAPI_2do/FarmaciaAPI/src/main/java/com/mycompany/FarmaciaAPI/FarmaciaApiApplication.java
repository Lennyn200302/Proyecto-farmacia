package com.mycompany.FarmaciaAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.mycompany")
@EnableJpaRepositories(basePackages = "com.mycompany.repository")
@EntityScan(basePackages = "com.mycompany.modelo")
public class FarmaciaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmaciaApiApplication.class, args);
	}

}
