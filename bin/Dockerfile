FROM maven

RUN curl -sSL https://github.com/amalgam8/amalgam8/releases/download/v0.4.0/a8sidecar.sh | sh

ENV A8_SERVICE=accountsservices:v1
ENV A8_ENDPOINT_PORT=8090
ENV A8_ENDPOINT_TYPE=http
ENV A8_REGISTRY_URL=http://ADM-a8-registry.mybluemix.net
ENV A8_REGISTRY_POLL=60s
ENV A8_CONTROLLER_URL=http://ADM-a8-controller.mybluemix.net
ENV A8_CONTROLLER_POLL=60s
ENV A8_LOG=enable_log


RUN apt-get install git

RUN git clone https://github.com/caprepo/digitalbanking-accountsservice.git

RUN cd /digitalbanking-accountsservice

RUN mvn -f /digitalbanking-accountsservice/pom.xml clean install -DskipTests
COPY newrelic/ /opt/
EXPOSE 8090
EXPOSE 6379

ENTRYPOINT ["a8sidecar", "--register", "--supervise", "java", "-jar", "-Dnewrelic-config-file=/opt/newrelic.yml", "-javaagent:/opt/newrelic.jar", "-Dspring.profiles.active=docker", "/digitalbanking-accountsservice/target/accountservices-1.0.war"]
