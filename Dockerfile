FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

COPY --from=build /app/src/main/resources/templates /app/templates
COPY --from=build /app/src/main/resources/static /app/static

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]