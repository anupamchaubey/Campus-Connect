# Use a lightweight runtime environment directly
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the pre-built JAR file from your local target directory
COPY target/*.jar app.jar

# Expose Spring Boot's default port
EXPOSE 8080

# Keep memory footprints strictly limited to fit the free tier container
ENTRYPOINT ["java", "-Xmx400m", "-Xms200m", "-jar", "app.jar"]
