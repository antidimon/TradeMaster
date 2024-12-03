FROM jelastic/maven:3.9.5-openjdk-21 as builder
WORKDIR /mvcService
COPY . /mvcService/.
RUN mvn -f /mvcService/pom.xml clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-alpine
WORKDIR /mvcService
COPY --from=builder /mvcService/target/*.jar /mvcService/*.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/mvcService/*.jar"]