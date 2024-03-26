package com.cooba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.cooba.mapper.**"})
@SpringBootApplication
public class JPAApplication {

	public static void main(String[] args) {
		SpringApplication.run(JPAApplication.class, args);
	}

}
