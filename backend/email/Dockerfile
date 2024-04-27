FROM maven:latest AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17
COPY --from=builder /app/target/email-0.0.1-SNAPSHOT.jar /email-service.jar
ENTRYPOINT ["java", "-jar", "/email-service.jar"]
