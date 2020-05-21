FROM maven:3.6.0-jdk-11-slim AS server-builder
COPY /server/org.eclipse.emfcloud.ecore.glsp/src /home/app/org.eclipse.emfcloud.ecore.glsp/src
COPY /server/org.eclipse.emfcloud.ecore.glsp/pom.xml /home/app/org.eclipse.emfcloud.ecore.glsp/pom.xml
COPY /server/ecore-backend-server/src /home/app/ecore-backend-server/src
COPY /server/ecore-backend-server/pom.xml /home/app/ecore-backend-server/pom.xml
COPY /server/pom.xml /home/app
WORKDIR /home/app
RUN mvn -f pom.xml clean package

FROM node:10.18.0-alpine3.10 as client-start

RUN mkdir /usr/src/client -p

WORKDIR /usr/src

RUN apk add --update python && \ 
	apk add --update make && \
	apk add --update g++ && \ 
	apk add --update openjdk11-jre

# Have to copy everything because the build statement in theia-ecore starts linting, which requires all files.
# "build": "tsc && yarn run lint"
COPY . .

WORKDIR /usr/src/client

RUN yarn install

RUN yarn rebuild:browser

WORKDIR /usr/src/client/browser-app

EXPOSE 3000

CMD yarn start --hostname 0.0.0.0