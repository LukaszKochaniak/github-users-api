FROM openjdk:11-jre-slim

ENV TZ=Europe/Warsaw

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 8080

COPY build/libs/*.jar /opt/github-users-api.jar

ENTRYPOINT exec java -jar /opt/github-users-api.jar