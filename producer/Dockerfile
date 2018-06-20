FROM frolvlad/alpine-oraclejdk8:slim
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/jars/producer.jar"]
