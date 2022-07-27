#FROM adoptopenjdk/openjdk11:alpine-jre
FROM openjdk:18-alpine3.13
EXPOSE 8085
ADD target/spring-boot-library-management-api.jar spring-boot-library-management-api.jar
RUN #apk --update add openjdk11-jre
ENTRYPOINT ["java", "-jar", "/spring-boot-library-management-api.jar"]
