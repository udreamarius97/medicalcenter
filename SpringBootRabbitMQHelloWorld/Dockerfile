FROM openjdk:11.0.8-jdk


# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8082 available to the world outside this container
EXPOSE 8082
EXPOSE 5672

# The application's jar file
ARG JAR_FILE=target/spring-boot-rabbitmq-producer-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} rabbitmq.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/rabbitmq.jar"]