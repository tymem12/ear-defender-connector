# EarDefender_connector

## Documentation

After launching application visit this URL for documentation:

If run manually: http://localhost:8080/swagger-ui/index.html

If run with docker-compose then: http://localhost:9090/swagger-ui/index.html


## Launch

- Install and run [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- Run `mvn clean package`
- Build an image `docker build -t ear-defender-connector-app:latest .`
- Run `docker-compose up` command to launch containers

### Docker commands

- `docker ps` - List running containers
- `docker ps -a` - List all containers
- `docker-compose up` - Run containers detailed in `docker-compose.yml`
- `docker-compose down` - Stop and remove containers detailed in `docker-compose.yml`
- `docker-compose down -v` - Stop and remove containers detailed in `docker-compose.yml` with volumes
- `docker start <container_id>` - Start a stopped container
- `docker stop <container_id>` - Stop a running container
- `docker exec -it <container_id> /bin/bash` - Open interactive shell of a container
- `docker-compose build` - Build docker image

## JaCoCo

To generate JaCoCo report run `mvn clean test`

Then go to [`target/site/jacoco/index.html`](target/site/jacoco/index.html)