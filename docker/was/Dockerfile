FROM openjdk:17-alpine

RUN apk update && apk add --no-cache iputils net-tools busybox-extras

COPY ./SmallBank.jar /home/was/SmallBank.jar

ENV JAVA_OPTS=''

ENTRYPOINT java ${JAVA_OPTS} -Dspring.profiles.active=release -jar /home/was/SmallBank.jar 