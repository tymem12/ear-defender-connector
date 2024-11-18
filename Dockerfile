FROM maven:3.8.3-openjdk-17 AS builder

WORKDIR /app

COPY lombok.config .
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/EarDefenderConnector-app.jar /app/eardefender-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/eardefender-app.jar"]
