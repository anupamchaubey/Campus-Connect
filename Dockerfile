# --- Stage 1: Build the application ---
# UPDATED: Shifted base image up to Java 21
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app

# Strict memory restrictions for the Maven engine
ENV MAVEN_OPTS="-Xmx300m -Xms128m -XX:+UseSerialGC"

# Copy dependency files first to leverage caching
COPY pom.xml .
RUN mvn dependency:go-offline -B -Dstyle.color=never

# Copy source code and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests -Dmaven.compiler.fork=true -Dstyle.color=never

# --- Stage 2: Run the application ---
# UPDATED: Shifted runtime environment up to Java 21 JRE
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the built JAR from Stage 1
COPY --from=build /app/target/*.jar app.jar

# Expose Spring Boot's default port
EXPOSE 8080

# Keep resource limits strictly within Render's free threshold
ENTRYPOINT ["java", "-Xmx400m", "-Xms200m", "-jar", "app.jar"]
