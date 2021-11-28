package com.mintonomous;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import com.mintonomous.service.MintMqttService;

@SpringBootApplication(scanBasePackages = "com.mintonomous")
@EnableScheduling
public class Mintonomous  implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(Mintonomous.class);

	@Autowired
	MintMqttService messagingService;


	public static void main(String... args) {
		SpringApplication.run(Mintonomous.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.",
				Arrays.toString(args));

		messagingService.subscribe();

		//messagingService.publish("Hi\nThierereres is a sample message published to topic roytuts/topic/event", 0, true);
		//context.close();

	}
	

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}
}
