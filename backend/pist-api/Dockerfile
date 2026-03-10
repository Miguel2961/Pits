# Build context debe ser esta carpeta (backend/pist-api). En Render: Docker Build Context = backend/pist-api
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline -B || true
COPY src ./src

RUN ./mvnw -DskipTests clean package -B

FROM eclipse-temurin:21-jre-alpine
RUN apk add --no-cache socat
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render exige que algo escuche en PORT desde el primer momento. Socat abre PORT y reenvía a la app (8080).
# La app tarda ~40s en arrancar; así Render detecta el puerto al instante y el tráfico llega cuando Java está listo.
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]
