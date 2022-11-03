FROM maven:3.8-openjdk-17 as builder
COPY . /app
WORKDIR /app
RUN mvn clean
RUN mvn -B package -Dmaven.test.skip --file pom.xml

FROM openjdk:17-alpine

ENV PORT=3000
ENV DB_PORT=5432
ENV DB_NAME=dbname
ENV DB_USER=dbuser
ENV DB_PASSWORD=dbpassword

WORKDIR application
COPY --from=builder /app/target/ipfs-rest-api.jar application.jar


ENTRYPOINT ["java", "-jar", "application.jar"]
