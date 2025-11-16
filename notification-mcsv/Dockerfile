
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY notification-mcsv/pom.xml ./notification-mcsv/

COPY . .

RUN mvn -pl notification-mcsv -am clean package -DskipTests

# ====================== Runtime image # ======================
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /app/notification-mcsv/target/notification-mcsv-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]
