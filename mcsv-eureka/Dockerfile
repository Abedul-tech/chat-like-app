FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/mcsv-eureka-0.0.1-SNAPSHOT.jar /app/mcsv-eureka.jar

# It documents that the container will listen on port 8888.
# It allows Docker to know which port to map if you run the container with -P or -p.
EXPOSE 8761

# Run the jar file
ENTRYPOINT ["java", "-jar", "mcsv-eureka.jar"]