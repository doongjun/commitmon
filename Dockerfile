FROM arm64v8/openjdk:21 AS TEMP_BUILD_IMAGE
MAINTAINER doongjun.kim@gmail.com
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY . ./
RUN microdnf install findutils
RUN ./gradlew build -x test --stacktrace

FROM arm64v8/openjdk:21
ENV ARTIFACT_NAME=commitmon-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
EXPOSE 8080
CMD ["java", "-jar", "commitmon-0.0.1-SNAPSHOT.jar"]