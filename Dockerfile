# Build the frontend assets
FROM node:20.18.0-alpine3.19 AS node-builder

WORKDIR /app

COPY package.json .
COPY package-lock.json .
COPY webpack.config.js .
COPY src/main/resources/static ./src/main/resources/static

RUN npm install
RUN npm run build

# Build the server .jar
FROM gradle:8.11.0-jdk21-alpine AS backend-builder

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .

COPY src ./src
COPY --from=node-builder /app/target/classes/static ./src/main/resources/static

RUN gradle build -x test -PbuildProfile=production

# Run the server
FROM openjdk:24-ea-21-jdk-slim-bullseye

WORKDIR /app
COPY --from=backend-builder /app/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
