package com.arka.inventorymcsv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
   @CreatedDate
   @Column(updatable = false)
   private LocalDateTime createdAt;

   @LastModifiedDate
   @Column(insertable = false)
   private LocalDateTime updatedAt;
}
