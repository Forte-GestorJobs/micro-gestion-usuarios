FROM openjdk:17-jdk-buster
VOLUME /tmp
ARG JAR_FILE=target/micro-gestion-usuarios-spring-boot.jar
ADD ${JAR_FILE} app.jar


ENTRYPOINT ["java","-jar","/app.jar"]