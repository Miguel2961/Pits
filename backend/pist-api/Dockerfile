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
ENV PORT=10000
ENV APP_PORT=8080
EXPOSE 10000
ENTRYPOINT ["sh", "-c", "socat TCP-LISTEN:${PORT},fork,reuseaddr TCP:localhost:${APP_PORT} & exec java -Dserver.port=${APP_PORT} -Dserver.address=0.0.0.0 -jar app.jar"]
