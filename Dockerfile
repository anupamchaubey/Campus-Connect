# ==========================================
# Stage 1: Build the application using Java 21 JDK
# ==========================================
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copy Maven wrapper files and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies using the wrapper
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application using the Maven Wrapper (guarantees Java 21 compilation)
RUN ./mvnw clean package -DskipTests -Dmaven.compiler.fork=true -Dstyle.color=never

# ==========================================
# Stage 2: Run the application using Java 21 JRE
# ==========================================
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Xmx400m", "-Xms200m", "-jar", "app.jar"]