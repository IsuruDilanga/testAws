package com.example.TestAws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TestAwsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestAwsApplication.class, args);
	}

}
