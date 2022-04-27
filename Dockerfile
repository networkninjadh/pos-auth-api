FROM adoptopenjdk/openjdk11:alpine-jre
CMD mnv clean package
COPY target/pos-auth-api-0.0.1-SNAPSHOT.jar pos-auth-api.jar
ENTRYPOINT ["java", "-jar", "pos-auth-api.jar"]
EXPOSE 8081