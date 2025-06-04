FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/mcsv-message-0.0.1-SNAPSHOT.jar /app/mcsv-message.jar

# Make port 8080 available to the world outside this container
EXPOSE 8050

# Run the jar file
ENTRYPOINT ["java", "-jar", "mcsv-message.jar"]