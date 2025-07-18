# Étape 1 : build
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY MedicalRecord-ms/pom.xml .
COPY MedicalRecord-ms/src ./src
RUN mvn clean package -DskipTests

# Étape 2 : runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app

RUN apt-get update && apt-get install -y netcat-openbsd && apt-get clean

COPY --from=build /app/target/*.jar app.jar
COPY scripts/wait-for-mysql.sh wait-for-mysql.sh
RUN chmod +x wait-for-mysql.sh

EXPOSE 8081
ENTRYPOINT ["./wait-for-mysql.sh"]
