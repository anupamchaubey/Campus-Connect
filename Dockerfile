# --- Stage 1: Build the application ---
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# CRITICAL FOR RENDER: Strict memory restrictions for the Maven compiler engine
ENV MAVEN_OPTS="-Xmx300m -Xms128m -XX:+UseSerialGC"

# Copy dependency files first
COPY pom.xml .

# FORCE SINGLE-THREAD DOWNLOAD: Saves massive amounts of RAM during downloading
RUN mvn dependency:go-offline -B -Dstyle.color=never

# Copy source code and build the JAR
COPY src ./src

# FORCE LOW MEMORY FOR COMPILATION: Keeps forks small
RUN mvn clean package -DskipTests -Dmaven.compiler.fork=true -Dstyle.color=never


# --- Stage 2: Run the application ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built JAR from the first stage
COPY --from=build /app/target/*.jar app.jar

# Expose Spring Boot's default port
EXPOSE 8080

# CRITICAL FOR FREE TIER: Limit Java memory to 400MB so Render doesn't kill the container
ENTRYPOINT ["java", "-Xmx400m", "-Xms200m", "-jar", "app.jar"]