#Dockerfile APP
FROM openjdk:11.0.9-jre

# Prepare workspace
RUN mkdir -p /usr/share/app

# Add the service itself
COPY target/quartz-0.0.1-SNAPSHOT.jar /usr/share/app/quartz-0.0.1-SNAPSHOT.jar

EXPOSE 8090

# Start service
RUN sh -c 'touch /usr/share/app/quartz-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java", "-jar", "/usr/share/app/quartz-0.0.1-SNAPSHOT.jar"]
