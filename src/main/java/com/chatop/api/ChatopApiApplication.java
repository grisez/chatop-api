package com.chatop.api;

import me.paulschwarz.springdotenv.spring.DotenvApplicationInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChatopApiApplication {

	public static void main(String[] args) {
		// spring-dotenv 5.1.0 ships without its Spring Boot auto-registration file,
		// so its initializer (which loads the .env file) is registered explicitly here.
		new SpringApplicationBuilder(ChatopApiApplication.class)
				.initializers(new DotenvApplicationInitializer())
				.run(args);
	}

}
