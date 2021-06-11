package com.zup.apigerenciadorcarros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ApiGerenciadorCarrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGerenciadorCarrosApplication.class, args);
	}

}
