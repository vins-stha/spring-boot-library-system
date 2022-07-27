FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR .
EXPOSE 8085
ADD target/spring-boot-library-management-api.jar spring-boot-library-management-api.jar
RUN apk --update add openjdk8-jre

ENTRYPOINT ["java", "-jar", "/spring-boot-library-management-api.jar"]
