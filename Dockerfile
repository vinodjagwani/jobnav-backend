FROM maven:3.8.3-openjdk-17 AS build
RUN mkdir app
WORKDIR /app
#Copy required files in container
COPY src src
COPY pom.xml .
COPY GATEFiles GATEFiles   
COPY JAPEGrammars JAPEGrammars
RUN mvn -f /app/pom.xml clean package install

FROM openjdk:11.0-jre
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app.jar"]
