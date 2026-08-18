# Step 1: Use a tiny runtime container (No heavy build tools or download tasks)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Step 2: Copy the pre-compiled package from your local laptop target folder
COPY target/*.jar app.jar

# Step 3: Set connection parameters
EXPOSE 8080

# Step 4: Run the package directly inside safe free-tier allocations
ENTRYPOINT ["java", "-Xmx400m", "-Xms200m", "-jar", "app.jar"]
