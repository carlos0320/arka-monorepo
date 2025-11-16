package com.arka.notificationmcsv.infrastructure.adapter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;


@Configuration
public class DynamoDBConfig {

  @Value("${aws.region}")
  private String awsRegion;

  // Optional: used only for local testing (DynamoDB local / LocalStack)
  @Value("${aws.dynamodb.endpoint:}")
  private String dynamoEndpoint;

  @Bean
  public DynamoDbClient dynamoDbClient() {
    var builder = DynamoDbClient.builder()
            .region(Region.of(awsRegion))
            .credentialsProvider(DefaultCredentialsProvider.create());


    if (dynamoEndpoint != null && !dynamoEndpoint.isBlank()) {
      builder = builder.endpointOverride(URI.create(dynamoEndpoint));
    }
    return builder.build();
  }

  @Bean
  public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
    return DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build();
  }
}
