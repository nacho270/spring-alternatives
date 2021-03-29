FROM openjdk:11

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE

ADD target/${JAR_FILE} jooq.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/jooq.jar"]
