#FILE USED TO GENERATE A IMAGE(BLUEPRINT) FROM THIS SERVICE
#It contains everything to run the service(container)
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/mcsv-websocket-0.0.1-SNAPSHOT.jar /app/mcsv-websocket.jar

# Make port 8080 available to the world outside this container
EXPOSE 8090

# Run the jar file
ENTRYPOINT ["java", "-jar", "mcsv-websocket.jar"]