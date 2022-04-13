FROM adoptopenjdk/openjdk11:alpine-jre
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ENV APP_HOME=/usr/pos-auth-api
ARG JAR_FILE=target/*.jar 
COPY POS-System.jar app.jar
EXPOSE 8081
CMD [ "java","-jar","app.jar"]