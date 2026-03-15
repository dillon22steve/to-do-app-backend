# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
# Create a directory for the app code
WORKDIR /app
# Copy everything from your project root into /app
COPY . .
# Run maven from inside /app (where pom.xml now lives)
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copy the built jar from the build stage's /app/target folder
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]