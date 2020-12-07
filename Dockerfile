# Build container
FROM maven:3.6.3-adoptopenjdk-11 as builder

# Create app directory
WORKDIR /usr/bin/app

# Copy source to working directory
COPY . .

# Build package
RUN mvn -Dmaven.test.skip=true clean package


# Pull build into a second stage deploy container
FROM adoptopenjdk/openjdk11:alpine

LABEL maintainer="Malith Wimalarathna"

# Install packages
RUN apk add --no-cache ca-certificates tzdata

# Copy application data
COPY --from=builder --chown=1001:1001 /usr/bin/app/target/smartcity-*.jar /usr/src/app/app.jar
COPY --from=builder --chown=1001:1001 /usr/bin/app/target/classes/application.properties /usr/src/app/application.properties
COPY --from=builder --chown=1001:1001 /usr/bin/app/target/classes/bootstrap.properties /usr/src/app/bootstrap.properties

USER 1001

WORKDIR /usr/bin/app

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar","--spring.config.location=application.properties","--spring.cloud.bootstrap.location=bootstrap.properties"]
