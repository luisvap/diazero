# -------- Stage 1: Build --------
FROM maven:3.9.5-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy the entire project and build
COPY . .
RUN mvn clean package

# -------- Stage 2: Run --------
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the jar from builder stage
COPY --from=builder /app/target/incident-api-*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
