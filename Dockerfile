# Build container
FROM maven:3.6.3-adoptopenjdk-11 as builder

# Create app directory
WORKDIR /usr/src/app

RUN ls -al /usr/src/app
# Copy source to working directory
COPY . .

RUN ls -al /

# Build package
RUN mvn -Dmaven.test.skip=true clean package

RUN ls -al /usr/src/app

# Pull build into a second stage deploy container
FROM adoptopenjdk/openjdk11:alpine

LABEL maintainer="Malith Wimalarathna"

# Install packages
RUN apk add --no-cache ca-certificates tzdata

# Copy application data
COPY --from=builder --chown=1001:1001 /usr/src/app/target/smartcity-*.jar /usr/src/app/app.jar
COPY --from=builder --chown=1001:1001 /usr/src/app/src/main/resources/application.properties /usr/src/app/application.properties
COPY --from=builder --chown=1001:1001 /usr/src/app/src/main/resources/bootstrap.properties /usr/src/app/bootstrap.properties

USER 1001

WORKDIR /usr/src/app

RUN chown 755 /usr/scr/app/static

EXPOSE 9001

ENTRYPOINT ["java","-jar","app.jar","--spring.config.location=application.properties","--spring.cloud.bootstrap.location=bootstrap.properties"]
