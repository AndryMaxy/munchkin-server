FROM amazoncorretto:17 AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and build files
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

# Copy your source code
COPY src ./src

# Give execution permission to the Gradle wrapper
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew build --no-daemon

# Use a lighter base image for the final container
FROM amazoncorretto:17

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port (default is 8080)
EXPOSE 9090

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

