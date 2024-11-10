FROM openjdk:17-slim

EXPOSE 8089

# Install curl
RUN apt-get update && apt-get install -y curl

# Download the JAR file from Nexus
RUN curl -o /kaddem-0.0.1.jar -L "http://192.168.33.10:8081/repository/maven-releases/tn/esprit/spring/kaddem/0.0.1/kaddem-0.0.1.jar"

# Set the entry point
ENTRYPOINT ["java", "-jar", "/kaddem-0.0.1.jar"]
