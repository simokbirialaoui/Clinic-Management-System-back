# Étape 1 : Compilation
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests -f identity-service/pom.xml

# Étape 2 : Runtime avec netcat
FROM eclipse-temurin:21-jdk
WORKDIR /app

RUN apt-get update && apt-get install -y netcat-openbsd && apt-get clean

COPY --from=build /app/identity-service/target/*.jar app.jar
COPY ../scripts/wait-for-mysql.sh .

RUN chmod +x wait-for-mysql.sh

ENV SPRING_PROFILES_ACTIVE=docker
EXPOSE 9898

# ✅ Cette ligne démarre Spring Boot APRÈS que la BDD soit prête
ENTRYPOINT ["./wait-for-mysql.sh", "java", "-jar", "app.jar"]
