FROM adoptopenjdk/openjdk15:alpine-jre
WORKDIR .
EXPOSE 8085
ADD target/spring-boot-library-management-api.jar spring-boot-library-management-api.jar
RUN apk --update add openjdk15-jre --repository=http://dl-cdn.alpinelinux.org/alpine/edge/testing/

ENTRYPOINT ["java", "-jar", "/spring-boot-library-management-api.jar"]
