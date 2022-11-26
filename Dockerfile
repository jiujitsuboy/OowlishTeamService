FROM openjdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8082
ENTRYPOINT ["java","-Dspring.profiles.active=local","-jar","/app.jar"]