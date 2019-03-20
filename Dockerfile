FROM maven:3.6-jdk-12-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B package
RUN ls -rtl /app/target


FROM openjdk:8-jre-alpine
COPY --from=builder /app/target/reactive-mongo-0.0.1-SNAPSHOT.jar /
CMD ["java", "-jar", "/reactive-mongo-0.0.1-SNAPSHOT.jar"]