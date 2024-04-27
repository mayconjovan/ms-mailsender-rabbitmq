FROM maven:latest AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests


FROM openjdk:17
COPY --from=builder /app/target/job-0.0.1-SNAPSHOT.jar /job-service.jar
ENTRYPOINT ["java", "-jar", "/job-service.jar"]
