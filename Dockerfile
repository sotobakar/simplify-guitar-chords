# Use the official OpenJDK 17 base image
FROM openjdk:17-jdk-slim AS build

# Set the working directory
WORKDIR /app

# Copy the Maven executable to the container
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Copy the project files and directories
COPY src src

# Build the application
RUN ./mvnw -DskipTests=true clean package

# Use the official OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build image
COPY --from=build /app/target/*.jar application.jar

# Expose the port that the application will run on
EXPOSE 8080

# Specify the default command to run the application
CMD ["java", "-jar", "application.jar"]
