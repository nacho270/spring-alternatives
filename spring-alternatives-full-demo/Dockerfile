FROM openjdk:11

EXPOSE 8080

ARG JAR_FILE

ADD target/${JAR_FILE} spring-alternatives.jar

ENTRYPOINT ["java","-jar","/spring-alternatives.jar"]
