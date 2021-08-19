FROM openjdk:11.0.8-jdk


# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8081
EXPOSE 5672
EXPOSE 8080
EXPOSE 3036
EXPOSE 6565

# The application's jar file
ARG JAR_FILE=target/Medicalcenter-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} medicalcenter.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/medicalcenter.jar"]