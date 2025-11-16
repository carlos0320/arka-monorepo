
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY cart-mcsv/pom.xml ./cart-mcsv/

COPY . .

RUN mvn -pl cart-mcsv -am clean package -DskipTests

# ====================== Runtime Stage # ======================
FROM eclipse-temurin:17-jdk

WORKDIR /app
COPY --from=builder /app/cart-mcsv/target/cart-mcsv-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
