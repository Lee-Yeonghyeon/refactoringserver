package com.example.refactoringserver;


import com.example.refactoringserver.telegram.RefactoryBot;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
public class RefactoringServerApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location = " + "classpath:application.properties," + "classpath:aws.yml";

	public static void main(String[] args) {

		ApiContextInitializer.init();

		TelegramBotsApi botsApi = new TelegramBotsApi();
		try{
			botsApi.registerBot(new RefactoryBot());
		} catch (TelegramApiRequestException e) {
			throw new RuntimeException(e);
		}

		new SpringApplicationBuilder(RefactoringServerApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
