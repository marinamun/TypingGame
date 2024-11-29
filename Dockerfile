# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy all files from your repository into the container
COPY . .

# Build the application
RUN javac -d out TypingGame.java GameLogic.java && jar cfm TypingGame.jar META-INF/MANIFEST.MF -C out .

# Expose a port if your app uses one
EXPOSE 8080

# Set the command to run your app
CMD ["java", "-jar", "TypingGame.jar"]
