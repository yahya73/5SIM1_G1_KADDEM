FROM openjdk:17-jdk
EXPOSE 8089
ADD target/kaddem-0.0.1-SNAPSHOT.jar Kaddem-1.0.jar
ENTRYPOINT ["java", "-jar", "/Kaddem-1.0.jar"]