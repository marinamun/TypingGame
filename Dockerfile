# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-alpine

# Install Xvfb and required libraries for AWT
RUN apk add --no-cache \
    xvfb \
    libxrender \
    libxtst \
    libxi \
    fontconfig \
    ttf-dejavu

# Set the working directory in the container
WORKDIR /app

# Copy all files from your repository into the container
COPY . .

# Build the Java application
RUN javac -d out TypingGame.java GameLogic.java && jar cfm TypingGame.jar META-INF/MANIFEST.MF -C out .

# Set the display environment variable to use Xvfb
ENV DISPLAY=:99

# Start Xvfb before running the application
CMD ["sh", "-c", "rm -f /tmp/.X99-lock && Xvfb :99 -screen 0 1024x768x16 & java -jar TypingGame.jar"]
