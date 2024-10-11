FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/EarDefenderConnector-app.jar /app/eardefender-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/eardefender-app.jar"]
