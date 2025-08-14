# Build a JAR File
FROM maven:3.8.2-jdk-8-slim AS stage1
WORKDIR /home/app
COPY . /home/app/
RUN mvn -f /home/app/pom.xml clean package

# Create an Image
FROM openjdk:8-jdk-alpine
EXPOSE 5000
COPY --from=stage1 /home/app/target/full-Stack-java.jar full-Stack-java.jar
ENTRYPOINT ["sh", "-c", "java -jar /full-Stack-java.jar"]
