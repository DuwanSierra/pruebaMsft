#
# Build stage
#
FROM maven:3.6.3-jdk-14 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -P prod

#
# Package stage
#
FROM openjdk:14-jdk-slim
COPY --from=build  /home/app/target/MainSoft.jar /usr/local/lib/MainSoft.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/MainSoft.jar"]
