# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package

# Stage 2: Run
FROM eclipse-temurin:21-jammy
WORKDIR /app
# Копируем только готовый JAR из первой стадии
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]