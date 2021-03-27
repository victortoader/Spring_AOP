package com;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@Slf4j
@EnableCaching
@SpringBootApplication
public class ProxySpringApp {

	@Autowired
	private ExpensiveOps ops;

	public static void main(String[] args) {
		SpringApplication.run(ProxySpringApp.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		// Domain Logic
		// must be agnostic to technical details
		return args -> {
		log.debug("Object ExpensiveOps is: " + ops.getClass());
		log.debug("Is 10000169 a prime number? \n");
		log.debug("Got: " + ops.isPrime(10_000_169)+ "\n");
		log.debug("Is 10000169 a prime number? \n");
		log.debug("Got: " + ops.isPrime(10_000_169)+ "\n");
		};
	}
}
