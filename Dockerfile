FROM openjdk:8
COPY ./target/to-do-microservices.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "to-do-microservices.jar"]