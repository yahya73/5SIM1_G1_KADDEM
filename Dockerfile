# Start with a base image that has Java 17 installed
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/kaddem-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your application will run on
EXPOSE 8089

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
