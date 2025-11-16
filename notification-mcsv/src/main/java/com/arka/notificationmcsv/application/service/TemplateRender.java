package com.arka.notificationmcsv.application.service;

import com.arka.notificationmcsv.domain.model.EmailTemplate;
import com.arka.notificationmcsv.domain.model.gateway.TemplateRenderGateway;
import com.arka.notificationmcsv.infrastructure.messaging.orderDto.OrderEventDto;

import java.util.List;
import java.util.Map;

public class TemplateRender implements TemplateRenderGateway {

  @Override
  public String render(EmailTemplate template, Map<String, Object> emailData) {
    String body = template.getBody();

    body = processItems(body, emailData);

    for (Map.Entry<String, Object> entry : emailData.entrySet()) {
      if (!(entry.getValue() instanceof List)) {
        body = body.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
      }
    }

    return body;
  }

  private String processItems(String body, Map<String, Object> emailData) {
    for (Map.Entry<String, Object> entry : emailData.entrySet()) {
      if (entry.getValue() instanceof List<?> list) {
        String key = entry.getKey(); // e.g. "items"
        String startTag = "{{#" + key + "}}";
        String endTag = "{{/" + key + "}}";

        // Find section in template
        int start = body.indexOf(startTag);
        int end = body.indexOf(endTag);
        if (start == -1 || end == -1) continue;

        String sectionTemplate = body.substring(start + startTag.length(), end);
        StringBuilder renderedSection = new StringBuilder();

        for (Object obj : list) {
          if (obj instanceof OrderEventDto.OrderItemEvent item) {
            String renderedItem = sectionTemplate
                    .replace("{{quantity}}", item.getQuantity().toString())
                    .replace("{{productName}}", item.getProductName())
                    .replace("{{unitPrice}}", item.getUnitPrice().toPlainString())
                    .replace("{{totalPrice}}", item.getTotalPrice().toPlainString());
            renderedSection.append(renderedItem);
          }
        }

        // Replace section with rendered items
        body = body.replace(startTag + sectionTemplate + endTag, renderedSection.toString());
      }
    }
    return body;
  }
}
