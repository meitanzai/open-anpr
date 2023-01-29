FROM openjdk:11.0.11-jre-slim
MAINTAINER open-anpr
WORKDIR /app/open-anpr

#RUN sed -i 's/deb.debian.org/mirrors.aliyun.com/g' /etc/apt/sources.list && \
#apk update && apk add libssl1.0 libx11 libxext libxrender libstdc++ freetype fontconfig

COPY scripts/docker/entrypoint.sh /app/open-anpr
RUN chmod +x /app/open-anpr/entrypoint.sh

COPY open-anpr-core/src/main/resources/model /app/open-anpr/model

COPY open-anpr-server/target/open-anpr-server.jar /app/open-anpr/open-anpr-server.jar

EXPOSE 8080
ENTRYPOINT ["/app/open-anpr/entrypoint.sh"]
