# syntax=docker/dockerfile:1
FROM openjdk:11

ARG JAR_FILE=build/libs/munchkin-server.jar

COPY ${JAR_FILE} app.jar

EXPOSE 9090

ENTRYPOINT ["java","-jar","/app.jar"]


