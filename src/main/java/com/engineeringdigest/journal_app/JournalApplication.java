package com.engineeringdigest.journal_app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

	@Bean
	public CommandLineRunner mongoInfo(MongoTemplate mongoTemplate) {
		return args -> {
			try {
				String dbName = mongoTemplate.getDb().getName();
				System.out.println("[startup] Connected MongoDB database: " + dbName);
			} catch (Exception e) {
				System.out.println("[startup] Could not determine MongoDB database: " + e.getMessage());
			}
		};
	}

	@Bean
	public PlatformTransactionManager add(MongoTemplate mongoTemplate) {
		return new MongoTransactionManager(mongoTemplate.getMongoDatabaseFactory());
	}

}
