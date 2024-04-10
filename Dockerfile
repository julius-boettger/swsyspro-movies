# build with maven 3 and jdk 21
FROM maven:3-amazoncorretto-21 AS builder
WORKDIR /app

COPY ["pom.xml", "src", "./"]
RUN ["mvn", "clean", "package"]

# run with jdk 21
FROM amazoncorretto:21
WORKDIR /app

COPY --from=builder ["target/*.jar", "app.jar"]

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]