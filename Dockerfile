FROM eclipse-temurin:21-jre

WORKDIR /app

COPY build/libs/notification-service-0.0.1-SNAPSHOT.jar app.jar
COPY opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

EXPOSE 8080

ENTRYPOINT ["java -javaagent:/otel/opentelemetry-javaagent.jar", "-jar", "/app/app.jar"]
