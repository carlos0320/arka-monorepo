
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml ./
COPY api-gateway/pom.xml ./api-gateway/pom.xml
COPY api-gateway ./api-gateway

RUN mvn -f api-gateway/pom.xml clean package -DskipTests

# ===== Runtime image =====
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=builder /app/api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java","-jar","app.jar"]
