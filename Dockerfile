FROM eclipse-temurin:17-jdk-alpine as builder

WORKDIR /app

COPY ./pom.xml .

COPY ./mvnw .
COPY ./.mvn ./.mvn

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/Secure-Login-0.0.1-SNAPSHOT.jar .

ENV PORT 8080

EXPOSE $PORT

CMD ["java", "-jar", "Secure-Login-0.0.1-SNAPSHOT.jar"]