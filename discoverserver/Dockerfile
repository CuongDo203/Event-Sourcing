# Stage 1: use maven for building
FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /app
COPY ./discoverserver /app/discoverserver
WORKDIR /app/discoverserver
RUN mvn clean package -DskipTests

# Stage 2: use OpenJDK for running
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/discoverserver/target/*.jar discoverserver.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar", "discoverserver.jar"]