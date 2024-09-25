FROM openjdk:22-jdk
MAINTAINER doongjun.kim@gmail.com
EXPOSE 8080
ARG JAR_FILE=build/libs/commitmon-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]