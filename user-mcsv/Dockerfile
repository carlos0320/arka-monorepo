FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml ./
COPY user-mcsv/pom.xml ./user-mcsv/pom.xml
COPY user-mcsv ./user-mcsv

RUN mvn -f user-mcsv/pom.xml clean package -DskipTests

# ===== Runtime image =====
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=builder /app/user-mcsv/target/user-mcsv-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]
