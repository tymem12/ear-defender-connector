# 🔗 EarDefender – Connector

**Service orchestration, analysis coordination & inter-module communication**

## 🚀 Overview

The **Connector** module is the central coordination service within the EarDefender architecture.

Its responsibilities include:

- Managing the internal state of ongoing analyses

- Handling communication between the Scraper, Detector, and Frontend modules

- Providing a consistent REST API for all system interactions

- Storing and accessing metadata in the database


The Connector is built using **Spring Boot** and exposes a well-documented REST interface for seamless integration with other services.


---

## 📘 API Documentation

Swagger UI is available after launching the service:

Local run:

👉 http://localhost:8080/swagger-ui/index.html

Docker Compose run:

👉 http://localhost:9090/swagger-ui/index.html



---

⚙️ Launch Instructions

Using Docker Compose (recommended)

`docker-compose up --build`

Manual Build & Run

1. Install and start Docker Desktop


2. Build the JAR:

`mvn clean package`


3. Build the Docker image:

`docker build -t ear-defender-connector-app:latest .`


4. Start containers:

`docker-compose up`


---

## 🧪 Test Coverage (JaCoCo)

To generate the JaCoCo coverage report:

`mvn clean test`

Then open the report locally at:

`target/site/jacoco/index.html`
