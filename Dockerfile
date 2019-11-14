FROM maven:3.6.2-jdk-11 as build
RUN mkdir -p /opt/app
WORKDIR /opt/app
COPY pom.xml /opt/app/
RUN mvn clean install -DskipTests
COPY src /opt/app/src
RUN mvn package -DskipTests
EXPOSE 8080
CMD ["mvn", "exec:java"]

