package com.arka.notificationmcsv.domain.usecase;

import com.arka.notificationmcsv.domain.model.*;
import com.arka.notificationmcsv.domain.model.gateway.EmailSenderGateway;
import com.arka.notificationmcsv.domain.model.gateway.EmailTemplateGateway;
import com.arka.notificationmcsv.domain.model.gateway.NotificationGateway;
import com.arka.notificationmcsv.domain.model.gateway.TemplateRenderGateway;

import java.time.LocalDateTime;
import java.util.Map;

public class SendOrderNotificationUseCase {
  private final NotificationGateway notificationGateway;
  private final EmailSenderGateway emailSenderGateway;
  private final EmailTemplateGateway emailTemplateGateway;

  private final TemplateRenderGateway templateRenderGateway;

  public SendOrderNotificationUseCase(NotificationGateway notificationGateway, EmailSenderGateway emailSenderGateway, EmailTemplateGateway emailTemplateGateway, TemplateRenderGateway templateRenderGateway) {
    this.notificationGateway = notificationGateway;
    this.emailSenderGateway = emailSenderGateway;
    this.emailTemplateGateway = emailTemplateGateway;
    this.templateRenderGateway = templateRenderGateway;
  }

  public void execute(Long userId, String userEmail, NotificationType type, Map<String, Object> emailData) {
    try{
      EmailTemplate emailTemplate = emailTemplateGateway.findByType(type); // obtenemos la plantilla (tipo)
      String emailBody = templateRenderGateway.render(emailTemplate, emailData); // construimos el mensaje con la plantilla

      EmailSendResult result = emailSenderGateway.sendEmail(userEmail, emailTemplate.getSubject(), emailBody); // enviamos el mensaje

      Notification notification = new Notification();
      notification.setType(type);
      notification.setUserId(userId);
      notification.setUserEmail(userEmail);
      notification.setCreatedAt(LocalDateTime.now());
      notification.setStatus(result.success()
              ? NotificationStatus.DELIVERED
              : NotificationStatus.ERROR);

      notification.setErrorLogs(result.error());

      // guardamos el registro del envio en mongodb
      notificationGateway.save(notification);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

}
