# Use the official Gradle image with JDK 17 as the base for building the app
FROM gradle:8.3.0-jdk17 as build

# Set the working directory inside the container
WORKDIR /app

# Copy only necessary files for dependency resolution (build.gradle, settings.gradle)
COPY build.gradle settings.gradle ./

# Download dependencies without building the app (this speeds up subsequent builds)
RUN gradle dependencies --no-daemon

# Copy the source code into the container
COPY src ./src

# Build the application (creates the jar file in build/libs)
RUN gradle bootJar --no-daemon

# Use a lightweight OpenJDK 17 image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory inside the runtime container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8080 (default port for Spring Boot applications)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
