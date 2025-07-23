# Étape 1 : Compilation
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY gateway-service/pom.xml .
COPY gateway-service/src ./src

RUN mvn clean package -DskipTests

# Étape 2 : Runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
