# ---------- build stage ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# download dependencies first so this layer is cached until pom.xml changes
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- run stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# do not run as root; /app/uploads is where uploaded documents and images go
RUN useradd --system --create-home appuser \
    && mkdir -p /app/uploads \
    && chown -R appuser /app

COPY --from=build --chown=appuser /app/target/*.jar app.jar

USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]