# Use OpenJDK image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file (update name if needed)
COPY target/*.jar app.jar

# Expose service port
EXPOSE 8086

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
