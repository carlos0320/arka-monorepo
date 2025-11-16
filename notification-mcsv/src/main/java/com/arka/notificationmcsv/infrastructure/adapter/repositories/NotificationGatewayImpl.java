package com.arka.notificationmcsv.infrastructure.adapter.repositories;

import com.arka.notificationmcsv.domain.model.Notification;
import com.arka.notificationmcsv.domain.model.gateway.NotificationGateway;
import com.arka.notificationmcsv.infrastructure.adapter.entities.NotificationDynamoEntity;
import com.arka.notificationmcsv.infrastructure.adapter.mapper.NotificationDynamoMapper;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

public class NotificationGatewayImpl implements NotificationGateway {
  private final DynamoDbTable<NotificationDynamoEntity> notificationTable;

  public NotificationGatewayImpl(DynamoDbTable<NotificationDynamoEntity> notificationTable) {
    this.notificationTable = notificationTable;
  }

  @Override
  public Notification save(Notification notification) {
    NotificationDynamoEntity entity = NotificationDynamoMapper.toDynamoEntity(notification);
    notificationTable.putItem(entity);
    return NotificationDynamoMapper.toDomain(entity);
  }
}
