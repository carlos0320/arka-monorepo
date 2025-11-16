
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml .
COPY order-mcsv/pom.xml ./order-mcsv/

COPY . .

RUN mvn -pl order-mcsv -am clean package -DskipTests

# ====================== Runtime image # ======================
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=builder /app/order-mcsv/target/order-mcsv-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]
