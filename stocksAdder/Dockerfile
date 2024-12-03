FROM jelastic/maven:3.9.5-openjdk-21 as builder
WORKDIR /scheduler
COPY . /scheduler/.
RUN mvn -f /scheduler/pom.xml clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-alpine
WORKDIR /scheduler
COPY --from=builder /scheduler/target/*.jar /scheduler/*.jar
EXPOSE 8050
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/scheduler/*.jar"]