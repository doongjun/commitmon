FROM openjdk:22-jdk
EXPOSE 8080
ARG JAR_FILE=build/libs/commitmon-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]