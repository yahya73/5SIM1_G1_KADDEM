FROM openjdk:17-jdk
EXPOSE 8089
ADD target/*.jar Kaddem-1.0.jar
ENTRYPOINT ["java", "-jar", "/kaddem-1.0.jar"]