FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /app

COPY ./borrowingservice ./borrowingservice
COPY ./commonservice ./commonservice

WORKDIR /app/commonservice
RUN mvn clean install -DskipTests

WORKDIR /app/borrowingservice
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/borrowingservice/target/*.jar borrowingservice.jar
EXPOSE 9003
ENTRYPOINT ["java","-jar", "borrowingservice.jar"]