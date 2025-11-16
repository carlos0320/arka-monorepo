package com.arka.notificationmcsv.infrastructure.adapter.aws.ses;

import com.arka.notificationmcsv.domain.model.EmailSendResult;
import com.arka.notificationmcsv.domain.model.gateway.EmailSenderGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.*;

@Slf4j
@Component
public class EmailSenderGatewayImpl implements EmailSenderGateway {
  private final SesV2Client sesClient;
  private final String fromAddress;

  public EmailSenderGatewayImpl(
          SesV2Client sesClient,
          @Value("${notification.email.from}")  String fromAddress
  ) {
    this.sesClient = sesClient;
    this.fromAddress = fromAddress;
  }

  @Override
  public EmailSendResult sendEmail(String to, String subject, String htmlBody) {
    try{
      // 1) Destinatario
      Destination destination = Destination.builder()
              .toAddresses(to)
              .build();

      // 2) Construimos el contenido: Asunto + Cuerpo del mensaje
      Content subjectContent = Content.builder()
              .data(subject)
              .charset("UTF-8")
              .build();

      Content htmlContent = Content.builder()
              .data(htmlBody)
              .charset("UTF-8")
              .build();

      Body body = Body.builder()
              .html(htmlContent)
              .build();

      Message message = Message.builder()
              .subject(subjectContent)
              .body(body)
              .build();

      EmailContent emailContent = EmailContent.builder()
              .simple(message)
              .build();

      // 3) Construimos el request
      SendEmailRequest request = SendEmailRequest.builder()
              .fromEmailAddress(fromAddress)
              .destination(destination)
              .content(emailContent)
              .build();

      // 4) LLamamos a aws SES
      SendEmailResponse response = sesClient.sendEmail(request);

      String messageId = response.messageId();
      log.info("SES email sent successfully. to={}, messageId={}", to, messageId);

      return new EmailSendResult(true, messageId, null);

    }catch (SesV2Exception e) {
      // SES-specific error handling
      log.error("Error sending email via SES to {}: {} ({})",
              to, e.awsErrorDetails().errorMessage(), e.awsErrorDetails().errorCode(), e);

      return new EmailSendResult(false, null,
              e.awsErrorDetails() != null ? e.awsErrorDetails().errorMessage() : e.getMessage());
    } catch (Exception e) {
      // Generic safeguard
      log.error("Unexpected error sending email via SES to {}", to, e);
      return new EmailSendResult(false, null, e.getMessage());
    }
  }
}
