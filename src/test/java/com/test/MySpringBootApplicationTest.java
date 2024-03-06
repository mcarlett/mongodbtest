package com.test;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import com.mongodb.client.MongoClient;

import java.io.ByteArrayInputStream;

@SpringBootTest
@CamelSpringBootTest
public class MySpringBootApplicationTest {

	@Autowired
	private CamelContext camelContext;

	@Autowired
	private MongoClient mongoClient;

	@Autowired
	private ProducerTemplate producerTemplate;

	private static MongoDBContainer mongoDBContainer;

	@DynamicPropertySource
	static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("mongodburl", () -> mongoDBContainer.getReplicaSetUrl());
	}

	@BeforeAll
	public static void init() {
		mongoDBContainer = new MongoDBContainer("mongo:4.4");
		mongoDBContainer.start();
	}

	@Test
	public void test() {
		mongoClient.getDatabase("test")
				.getCollection("test")
				.insertOne(new Document("msg", "hello world"));

		Assertions.assertThatCode(() -> producerTemplate.requestBody("direct:readMongo", new ByteArrayInputStream("{ msg: { $exists: true } }".getBytes())))
						.doesNotThrowAnyException();
	}
}
