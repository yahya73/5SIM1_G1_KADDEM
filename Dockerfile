FROM openjdk:17-jdk
EXPOSE 8089
COPY --from=builder /app/target/kaddem-1.0.jar /kaddem-1.0.jar
ENTRYPOINT ["java", "-jar", "/kaddem-1.0.jar"]