# Étape 1 : Compilation
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY MedicalRecord-ms/pom.xml .
COPY MedicalRecord-ms/src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Runtime avec netcat
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Installer netcat (pour wait-for-mysql.sh)
RUN apt-get update && apt-get install -y netcat-openbsd && apt-get clean

# Copier le jar et le script d'attente depuis le bon dossier
COPY --from=build /app/target/*.jar app.jar
COPY ./scripts/wait-for-mysql.sh .

# Donner les droits d’exécution au script
RUN chmod +x wait-for-mysql.sh

EXPOSE 8081

ENTRYPOINT ["./wait-for-mysql.sh"]
