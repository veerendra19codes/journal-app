package com.engineeringdigest.journal_app.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.engineeringdigest.journal_app.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient(@Value("${spring.data.mongodb.uri}") String mongoUri) {
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate(
            MongoClient mongoClient,
            @Value("${spring.data.mongodb.database}") String databaseName) {
        return new MongoTemplate(mongoClient, databaseName);
    }

    @Bean
    public org.springframework.boot.CommandLineRunner ensureIndexes(MongoTemplate mongoTemplate) {
        return args -> mongoTemplate.indexOps(UserEntity.class)
                .createIndex(new Index().on("userName", Sort.Direction.ASC).unique());
    }
}