# Use an official OpenJDK runtime as a parent image according to java version
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application's jar file to the container
COPY target/mcsv-auth-0.0.1-SNAPSHOT.jar /app/mcsv-auth.jar

# It documents that the container will listen on port 8888.
# It allows Docker to know which port to map if you run the container with -P or -p.
# docker run -P my-image
# available inside the Docker network only (for other containers).
EXPOSE 8060

# Run the jar file
ENTRYPOINT ["java", "-jar", "mcsv-auth.jar"]