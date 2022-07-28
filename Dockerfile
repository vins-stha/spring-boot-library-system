FROM openjdk:18-alpine3.13
EXPOSE 8083
RUN mvn clean package
ADD target/*.jar spring-boot-library-management-api.jar

ENTRYPOINT ["java", "-jar", "/spring-boot-library-management-api.jar"]
