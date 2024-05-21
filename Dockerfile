# Utilisez une image de base avec Java préinstallé
FROM openjdk:11-jre-slim

# Créez un répertoire pour l'application
RUN mkdir /app

# Définissez le répertoire de travail comme répertoire de l'application
WORKDIR /app

# Copiez le fichier JAR de votre application dans le conteneur
COPY target/votre_application.jar /app/app.jar

# Exposez le port sur lequel votre application Spring Boot fonctionnera
EXPOSE 8080

# Commande pour démarrer l'application
CMD ["java", "-jar", "app.jar"]
