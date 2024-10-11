# EarDefender_connector

## Documentation

After launching application visit this URL for documentation:
http://localhost:8080/swagger-ui/index.html

## Launch

Install and run Docker Desktop
https://www.docker.com/products/docker-desktop/

Run `docker-compose up` command to launch mongo container

### Docker commands

- `docker ps` - List running containers
- `docker ps -a` - List all containers
- `docker-compose up` - Run containers detailed in `docker-compose.yml`
- `docker-compose down -v` - Stop and remove containers detailed in `docker-compose.yml`
- `docker-compose down -v` - Stop and remove containers detailed in `docker-compose.yml` with volumes
- `docker start <container_id>` - Start a stopped container
- `docker stop <container_id>` - Stop a running container
- `docker rm <container_id>` - Remove stopped container
- `docker exec -it <container_id> /bin/bash` - Open interactive shell of a container
