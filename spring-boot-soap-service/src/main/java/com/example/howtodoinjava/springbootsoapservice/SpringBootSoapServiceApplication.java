package com.example.howtodoinjava.springbootsoapservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackageClasses = {
		SpringBootSoapServiceApplication.class
})public class SpringBootSoapServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSoapServiceApplication.class, args);
	}
}

//java -jar target\spring-boot-soap-service-0.0.1-SNAPSHOT.jar