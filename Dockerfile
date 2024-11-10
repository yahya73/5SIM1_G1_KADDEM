FROM openjdk:17-slim

EXPOSE 8089

 #Installer curl
RUN apt-get update && apt-get install -y curl

RUN curl -o kaddem-0.0.1.jar -L "http://192.168.33.10:8081/repository/maven-releases/http://192.168.33.10:8081/repository/maven-releases/tn/esprit/spring/kaddem/0.0.1/kaddem-0.0.1.jar"
ENTRYPOINT ["java", "-jar", "/kaddem-0.0.1.jar"]