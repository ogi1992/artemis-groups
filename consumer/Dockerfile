FROM java:openjdk-8-jre-alpine
ARG jar
ARG port
EXPOSE $port
COPY $jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
