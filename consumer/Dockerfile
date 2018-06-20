FROM frolvlad/alpine-oraclejdk8:slim
EXPOSE 8082
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/jars/consumer.jar"]
