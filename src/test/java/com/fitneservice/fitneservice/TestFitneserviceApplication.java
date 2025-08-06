package com.fitneservice.fitneservice;

import org.springframework.boot.SpringApplication;

public class TestFitneserviceApplication {

	public static void main(String[] args) {
		SpringApplication.from(FitneserviceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
