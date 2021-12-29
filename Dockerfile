# syntax=docker/dockerfile:1

FROM gradle:6.9.1-jdk11

COPY gradle ./gradle
COPY gradlew ./
COPY gradlew.bat ./
COPY src ./src

RUN ./gradlew build


