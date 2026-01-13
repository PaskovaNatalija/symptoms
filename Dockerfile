#FROM openjdk:21-jdk-slim

#COPY target/quarkus-app/quarkus-run.jar /docker-symptoms.jar
#COPY target/quarkus-app/quarkus-app-dependencies.txt /
#COPY target/quarkus-app/quarkus /
#
#EXPOSE 11000
#
#CMD ["java", "-jar", "/docker-symptoms.jar"]


#FROM registry.access.redhat.com/ubi8/openjdk-17:1.17
#
#ENV LANGUAGE='en_US:en'
#
#COPY --chown=185 target/quarkus-app/lib/ /deployments/lib/
#COPY --chown=185 target/quarkus-app/quarkus-run.jar /deployments/
#COPY --chown=185 target/quarkus-app/app/ /deployments/app/
#COPY --chown=185 target/quarkus-app/quarkus/ /deployments/quarkus/
#
#USER 185
#EXPOSE 8080
#
#ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
#ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"
#
#ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]

#FROM openjdk:21-jdk-slim
#VOLUME /tmp
#ARG JAR_FILE=target/quarkus-app/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]