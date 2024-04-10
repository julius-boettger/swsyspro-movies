# build with maven 3 and jdk 21
FROM maven:3-amazoncorretto-21 AS builder
WORKDIR /app

COPY ["pom.xml", "mvnw", "src", "./"]
# maven wrapper from spring initializer
RUN ["./mvnw", "install"]

# run with jdk 21
FROM amazoncorretto:21
WORKDIR /app

COPY --from=builder ["target/*.jar", "app.jar"]

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]