# Use OpenJDK 17 from the official Docker Hub repository
FROM openjdk:17-jdk-slim

# Set a working directory
WORKDIR /app

# Define an environment variable for the JAR file
ARG JAR_FILE=target/*.jar

# Copy the JAR file into the container at /app/app.jar
COPY ${JAR_FILE} app.jar

# Specify the command to run on container startup
CMD ["java", "-jar", "app.jar"]
