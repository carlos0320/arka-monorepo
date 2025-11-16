package com.arka.ordermcsv.infrastructure.adapter.repositories.event;


import com.arka.ordermcsv.infrastructure.adapter.entities.event.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface OutboxEntityRepository extends JpaRepository<OutboxEventEntity, UUID> {
  List<OutboxEventEntity> findByPublishedFalse();

  @Transactional
  @Modifying
  @Query("UPDATE OutboxEventEntity e SET e.published = true WHERE e.id = :id")
  void markAsPublished(@Param("id") UUID id);
}
