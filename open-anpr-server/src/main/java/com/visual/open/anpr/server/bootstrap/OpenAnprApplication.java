package com.visual.open.anpr.server.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class OpenAnprApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenAnprApplication.class, args);
	}

}
