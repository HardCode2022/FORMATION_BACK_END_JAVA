# Build a JAR File
FROM maven:3.9.6-eclipse-temurin-17 AS stage1
WORKDIR /home/app
COPY . /home/app/
RUN mvn -f /home/app/pom.xml clean package

# Create an Image
FROM eclipse-temurin:17-jdk-jammy
EXPOSE 5000
COPY --from=stage1 /home/app/target/FormationJavaAngularRestApi-0.0.1-SNAPSHOT.jar FormationJavaAngularRestApi-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["sh", "-c", "java -jar /FormationJavaAngularRestApi-0.0.1-SNAPSHOT.jar "]