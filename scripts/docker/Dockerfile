FROM openjdk:11.0.11-jre-slim
MAINTAINER open-anpr
WORKDIR /app/open-anpr

COPY scripts/docker/entrypoint.sh /app/open-anpr
RUN chmod +x /app/open-anpr/entrypoint.sh

COPY open-anpr-core/src/main/resources/models /app/open-anpr/models

COPY open-anpr-server/target/open-anpr-server.jar /app/open-anpr/open-anpr-server.jar

EXPOSE 8080
ENTRYPOINT ["/app/open-anpr/entrypoint.sh"]
