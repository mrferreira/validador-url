# Build stage
#
FROM maven:3.6.3-jdk-8-slim AS build
WORKDIR /home/app/
COPY . .
RUN mvn clean package -DskipTests
#

# Package stage
#
FROM openjdk:8-jre-alpine
WORKDIR "/usr/local/lib/"
COPY --from=build /home/app/target/*.jar ./validador-url.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","./validador-url.jar"]

