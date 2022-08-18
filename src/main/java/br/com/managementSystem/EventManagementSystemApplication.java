package br.com.managementSystem;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableAsync
public class EventManagementSystemApplication {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		SpringApplication.run(EventManagementSystemApplication.class, args);
	}

}
