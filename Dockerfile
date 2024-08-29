FROM maven:3.8.6-amazoncorretto-17 AS build
COPY pom.xml /build/
COPY checkstyle.xml /build/
WORKDIR /build/
RUN mvn dependency:go-offline
COPY src /build/src/
COPY db /build/db/
RUN mvn package -DskipTests

# Run stage
FROM openjdk:17-alpine
ARG JAR_FILE=/build/target/*.jar
COPY --from=build $JAR_FILE /opt/job4j_url_shortcut/app.jar
COPY --from=build /build/db/ /opt/job4j_url_shortcut/db/
ENTRYPOINT ["java", "-jar", "/opt/job4j_url_shortcut/app.jar"]