FROM eclipse-temurin:21 as build
WORKDIR /app

COPY ["src", "src"]
COPY ["mvnw", "pom.xml", "./"]
COPY [".mvn/wrapper/maven-wrapper.properties", ".mvn/wrapper/maven-wrapper.properties"]

# build jar
RUN ["./mvnw", "package", "-DskipTests"]
# extract jar to target/dependency
RUN ["mkdir", "-p", "target/dependency"]
RUN ["sh", "-c", "cd target/dependency && jar -xf ../*.jar"]

FROM eclipse-temurin:21

COPY --from=build ["/app/target/dependency/BOOT-INF/lib",     "/app/lib"]
COPY --from=build ["/app/target/dependency/META-INF",         "/app/META-INF"]
COPY --from=build ["/app/target/dependency/BOOT-INF/classes", "/app"]

ENTRYPOINT ["java", "-cp", "app:/app/lib/*", "swsyspro.movies.MoviesApplication"]