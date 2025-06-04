FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/mcsv-config-0.0.1-SNAPSHOT.jar /app/mcsv-config.jar

# Make port available to the world outside this container
EXPOSE 8888

# Run the jar file
ENTRYPOINT ["java", "-jar", "mcsv-config.jar"]