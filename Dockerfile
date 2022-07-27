FROM openjdk:8-alpine3.7
WORKDIR .
EXPOSE 8085
ADD target/spring-boot-library-management-api.jar spring-boot-library-management-api.jar
ENTRYPOINT ["java", "-jar", "/spring-boot-library-management-api.jar"]
