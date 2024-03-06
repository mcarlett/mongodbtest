package com.test;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDBConfiguration {
    @Bean
    public MongoClient mongoClient(@Value("${mongodburl}") String mongoUrl) {
        return MongoClients.create(mongoUrl);
    }
}
