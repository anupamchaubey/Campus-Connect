# --- Stage 1: Build the application ---
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy dependency files first to leverage Docker caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# --- Stage 2: Run the application ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built JAR from the first stage
COPY --from=build /app/target/*.jar app.jar

# Expose Spring Boot's default port
EXPOSE 8080

# CRITICAL FOR FREE TIER: Limit Java memory to 400MB so Render doesn't kill the container
ENTRYPOINT ["java", "-Xmx400m", "-Xms200m", "-jar", "app.jar"]