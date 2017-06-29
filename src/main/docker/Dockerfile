FROM maven
ADD accountservices-1.0.war app.war
EXPOSE 8090:8090
RUN sh -c 'touch /app.war'
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-jar","/app.war"]
