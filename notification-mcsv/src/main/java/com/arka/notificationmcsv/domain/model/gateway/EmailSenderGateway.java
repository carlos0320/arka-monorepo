package com.arka.notificationmcsv.domain.model.gateway;

import com.arka.notificationmcsv.domain.model.EmailSendResult;

public interface EmailSenderGateway {
  EmailSendResult sendEmail(String to, String subject, String body);
}
