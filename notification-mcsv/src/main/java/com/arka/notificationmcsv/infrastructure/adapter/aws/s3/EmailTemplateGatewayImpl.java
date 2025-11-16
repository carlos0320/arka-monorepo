package com.arka.notificationmcsv.infrastructure.adapter.aws.s3;

import com.arka.notificationmcsv.domain.model.EmailTemplate;
import com.arka.notificationmcsv.domain.model.NotificationType;
import com.arka.notificationmcsv.domain.model.gateway.EmailTemplateGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class EmailTemplateGatewayImpl implements EmailTemplateGateway {
  private final S3Client s3Client;
  private final String bucket;
  private final String subjectKey;
  private final String bodyKey;

  public EmailTemplateGatewayImpl(S3Client s3Client,
                                  @Value("${notification.templates.bucket}") String bucket,
                                  @Value("${notification.templates.subject-key}") String subjectKey,
                                  @Value("${notification.templates.body-key}") String bodyKey) {
    this.s3Client = s3Client;
    this.bucket = bucket;
    this.subjectKey = subjectKey;
    this.bodyKey = bodyKey;
  }

  @Override
  public EmailTemplate findByType(NotificationType type) {
    String subject = getObjectAsString(subjectKey);
    String body = getObjectAsString(bodyKey);

    if (subject == null || body == null) {
      throw new IllegalStateException("Email template not found in S3 for bucket=" + bucket);
    }
    return new EmailTemplate(type, subject, body);
  }


  private String getObjectAsString(String key) {
    try {
      // obtenemos el archivo de s3 (key)
      GetObjectRequest request = GetObjectRequest.builder()
              .bucket(bucket)
              .key(key)
              .build();

      ResponseInputStream<?> s3Object = s3Client.getObject(request);

      try{
        //Lee los bytes y los convierte en caracteres legibles (texto)
        //BufferedReader se usa encima de eso para poder leer línea por línea de manera eficiente
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(s3Object, StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        String line;
        //lee cada linea del archivo
        while ((line = reader.readLine()) != null) {
          sb.append(line).append("\n");
        }
        // retornamos el contenido como string
        return sb.toString().trim();
      }catch (Exception e) {
        log.error("Error reading S3 object. bucket={}, key={}", bucket, key, e);
        return null;
      }

    } catch (SdkException e) {
      log.error("Error loading template from S3. bucket={}, key={}", bucket, key, e);
      return null;
    }
  }
}
