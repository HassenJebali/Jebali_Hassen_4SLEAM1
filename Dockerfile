FROM openjdk:17-jdk-slim

WORKDIR /app

# On copie le JAR correct (pas le .original)
COPY --from=builder /app/target/*.jar app.jar

# Ton Spring Boot écoute sur 8089
EXPOSE 8089

ENTRYPOINT ["java", "-jar", "app.jar"]
