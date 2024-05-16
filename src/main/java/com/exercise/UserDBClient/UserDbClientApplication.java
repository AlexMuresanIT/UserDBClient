package com.exercise.UserDBClient;

import com.exercise.UserDBClient.client.UserQLClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class UserDbClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserDbClientApplication.class, args);
	}

//	@Bean
//	ApplicationRunner applicationRunner() {
//		return args -> {
//			UserRESTClient client = new UserRESTClient();
//			client.createUser(new User(1L,"Alex","alex@gmail.com","pw","Medias"));
//			client.getUserById(1L);
//			client.getUserById(1L);
//			System.out.println(client.getStats());
//		};
//	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			UserQLClient client1 = new UserQLClient();
			client1.createUser();
			client1.randomNumbers();
			client1.getUsersSub();

		};
	}


}
