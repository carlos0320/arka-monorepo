package com.arka.notificationmcsv.infrastructure.adapter.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDbBean
public class NotificationDynamoEntity {

  private String id;
  private String userEmail;
  private Long userId;
  private String createdAt;
  private String type;
  private String emailMessage;
  private String status;
  private String errorLogs;

  @DynamoDbPartitionKey // primary key de la tabla
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  // usamos esto si necesitamos en el futuro filtrar por usuario y por fecha
  @DynamoDbSecondaryPartitionKey(indexNames = "userId-createdAt-index") // userId es el partitionkey
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @DynamoDbSecondarySortKey(indexNames = "userId-createdAt-index") // createdAt es el sort key
  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }
}
