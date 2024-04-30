FROM openjdk:latest AS mavenbuilder

WORKDIR /app
COPY . .

RUN ./mvnw -Dmaven.repo.local=/app/.m2 clean package -DskipTests

FROM openjdk:latest

COPY --from=mavenbuilder /app/target/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]

