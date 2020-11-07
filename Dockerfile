# Docker multi-stage build
 
# 1. Build the app with Maven
FROM maven:3-jdk-11
 
ADD . /app
WORKDIR /app
 
# Just echo to check list
RUN ls -l
 
# Run Maven build
RUN mvn clean install -DskipTests=true

# 2. Use the build artifact and then remove the build-container
FROM openjdk:11-jdk

EXPOSE 8090

MAINTAINER Doyun Geum
 
VOLUME /tmp

# Add Spring Boot app.jar to container
COPY --from=0 /app/target/*.jar app.jar
 
# Start Spring Boot app
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
