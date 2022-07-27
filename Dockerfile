FROM openjdk:18-alpine3.13
EXPOSE 8083
ADD target/spring-boot-library-management-api.jar spring-boot-library-management-api.jar

#ADD target/spring-boot-library-management-api.jar spring-boot-library-management-api.jar
ENTRYPOINT ["java", "-jar", "/spring-boot-library-management-api.jar"]
