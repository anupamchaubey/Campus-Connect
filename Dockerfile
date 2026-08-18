# ==========================================
# Stage 1: Build the application using Java 21 JDK
# ==========================================
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copy Maven wrapper and configuration files first (for better layer caching)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies (cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application (skipping tests for speed)
RUN ./mvnw clean package -DskipTests -Dmaven.compiler.fork=true -Dstyle.color=never

# ==========================================
# Stage 2: Run the application using Java 21 JRE
# ==========================================
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port and configure container limits
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx400m", "-Xms200m", "-jar", "app.jar"]