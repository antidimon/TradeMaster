FROM jelastic/maven:3.9.5-openjdk-21 as builder
WORKDIR /restService
COPY . /restService/.
RUN mvn -f /restService/pom.xml clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-alpine
WORKDIR /restService
COPY --from=builder /restService/target/*.jar /restService/*.jar
EXPOSE 8060
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/restService/*.jar"]