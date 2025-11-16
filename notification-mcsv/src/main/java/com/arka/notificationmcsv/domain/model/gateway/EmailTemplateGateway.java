package com.arka.notificationmcsv.domain.model.gateway;

import com.arka.notificationmcsv.domain.model.EmailTemplate;
import com.arka.notificationmcsv.domain.model.NotificationType;

public interface EmailTemplateGateway {
  EmailTemplate findByType(NotificationType type);
}
