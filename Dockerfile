# Pulldown Alpine for the base
# FROM gliderlabs/alpine:3.7
FROM openjdk:13-alpine3.8

# Use baseimage-docker's init system.
# CMD ["/sbin/my_init"]

# Get the JDK and other helpful packages
RUN apk --no-cache add curl wget jq

# Add in the basic release file and retreival script
WORKDIR /home
ADD release.json release.json
ADD get-release-ver.sh get-release-ver.sh

# Copy over the java classes and libs
ADD Updater/ Updater/
WORKDIR /home/Updater
# Compile the classes and jar them.

# Check that java is installed correctly
RUN javac --version
RUN ls -la
WORKDIR /home
ENTRYPOINT [ "/bin/sh" ]