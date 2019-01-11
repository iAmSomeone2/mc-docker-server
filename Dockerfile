# Pulldown Alpine for the base
FROM gliderlabs/alpine:3.7

# Use baseimage-docker's init system.
CMD ["/sbin/my_init"]

# Get the JDK and other helpful packages
RUN apk --no-cache add openjdk8 curl wget jq

# Add in the basic release file and retreival script
ADD ./release.json ./release.json
ADD ./get-release-ver.sh ./get-release-ver.sh

# Copy over the java class and libs
ADD ./gson-2.8.5.jar ./gson-2.8.5.jar

# Compile the classes and jar them.

# Check that java is installed correctly
ENTRYPOINT [ "javac --version" ]