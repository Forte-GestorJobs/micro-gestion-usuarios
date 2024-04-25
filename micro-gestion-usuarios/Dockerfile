FROM openjdk:17-jdk-buster
VOLUME /tmp
EXPOSE 8081
ARG JAR_FILE=target/micro-gestion-usuarios-spring-boot.jar
ADD ${JAR_FILE} app.jar

ENV MGU_DBHOST "localhost"
ENV MGU_DBNAME "postgres"
ENV MGU_DBPORT 5433
ENV MGU_DBPWD "123"
ENV MGU_DBUSER "postgres"


ENTRYPOINT ["java","-jar","/app.jar"]