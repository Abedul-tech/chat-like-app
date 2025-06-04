FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/mcsv-gateway-0.0.1-SNAPSHOT.jar /app/mcsv-gateway.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "mcsv-gateway.jar"]