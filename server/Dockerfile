FROM maven:3.6.0-jdk-11-slim AS builder
COPY org.eclipse.emfcloud.ecore.glsp/src /home/app/org.eclipse.emfcloud.ecore.glsp/src
COPY org.eclipse.emfcloud.ecore.glsp/pom.xml /home/app/org.eclipse.emfcloud.ecore.glsp/pom.xml
COPY ecore-backend-server/src /home/app/ecore-backend-server/src
COPY ecore-backend-server/pom.xml /home/app/ecore-backend-server/pom.xml
COPY pom.xml /home/app
WORKDIR /home/app
RUN mvn -f pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=builder /home/app/org.eclipse.emfcloud.ecore.glsp/target/org.eclipse.emfcloud.ecore.glsp-0.0.2-SNAPSHOT-glsp.jar /usr/local/lib/server.jar
EXPOSE 5007
ENTRYPOINT ["java","-jar","/usr/local/lib/server.jar"]