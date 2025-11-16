package com.arka.notificationmcsv.application;

import com.arka.notificationmcsv.application.service.TemplateRender;
import com.arka.notificationmcsv.domain.model.gateway.EmailSenderGateway;
import com.arka.notificationmcsv.domain.model.gateway.EmailTemplateGateway;
import com.arka.notificationmcsv.domain.model.gateway.NotificationGateway;
import com.arka.notificationmcsv.domain.model.gateway.TemplateRenderGateway;
import com.arka.notificationmcsv.domain.usecase.SendOrderNotificationUseCase;
import com.arka.notificationmcsv.infrastructure.adapter.entities.NotificationDynamoEntity;
import com.arka.notificationmcsv.infrastructure.adapter.repositories.NotificationGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class NotificationConfig {
  @Bean
  public TemplateRender templateRenderer() {
    return new TemplateRender();
  }


  @Bean
  public DynamoDbTable<NotificationDynamoEntity> notificationTable(DynamoDbEnhancedClient enhancedClient) {
    return enhancedClient.table("notifications", TableSchema.fromBean(NotificationDynamoEntity.class));
  }

  @Bean
  public NotificationGateway notificationGateway(
          DynamoDbTable<NotificationDynamoEntity> notificationTable
  ) {
    return new NotificationGatewayImpl(notificationTable);
  }


  @Bean
  public SendOrderNotificationUseCase sendOrderNotificationUseCase(
          NotificationGateway notificationGateway,
          EmailSenderGateway emailSenderGateway,
          EmailTemplateGateway emailTemplateGateway,
          TemplateRenderGateway templateRenderGateway
  ) {
    return new SendOrderNotificationUseCase(
            notificationGateway,
            emailSenderGateway,
            emailTemplateGateway,
            templateRenderGateway
    );
  }
}
