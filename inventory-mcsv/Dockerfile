FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml ./
COPY inventory-mcsv/pom.xml ./inventory-mcsv/pom.xml
COPY inventory-mcsv ./inventory-mcsv

RUN mvn -f inventory-mcsv/pom.xml clean package -DskipTests

# ----- Runtime image
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=builder /app/inventory-mcsv/target/inventory-mcsv-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","app.jar"]
