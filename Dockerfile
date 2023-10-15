FROM openjdk:17-jdk-slim
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} MAO-resource-service-1.0.jar
ENTRYPOINT ["java","-jar","/MAO-resource-service-1.0.jar"]
