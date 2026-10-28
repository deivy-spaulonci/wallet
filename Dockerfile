FROM maven:3.10.0-eclipse-temurin-27 AS build
WORKDIR /app
LABEL authors="d31vy"

COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests package

FROM eclipse-temurin:27-jre
WORKDIR /app

COPY --from=build /app/target/wallet-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]