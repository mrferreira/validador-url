FROM openjdk:8-jre-alpine
WORKDIR "/usr/local/lib/"
COPY /target/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./app.jar"]

