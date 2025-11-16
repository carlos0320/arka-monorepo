package com.arka.notificationmcsv.domain.model.gateway;

import com.arka.notificationmcsv.domain.model.EmailTemplate;

import java.util.Map;

public interface TemplateRenderGateway {
  String render(EmailTemplate template, Map<String, Object> emailData);
}
