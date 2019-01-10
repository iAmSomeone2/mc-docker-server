# Pulldown Alpine for the base
FROM gliderlabs/alpine:3.7

# Use baseimage-docker's init system.
CMD ["/sbin/my_init"]

# Get the JRE
RUN apk --no-cache add openjdk8-jre curl wget mozjs60
# RUN curl -s https://java.com/en/download/installed8.jsp | grep latest8Version

# Get jsawk to get the appropriate release of the server software
RUN curl -L http://github.com/micha/jsawk/raw/master/jsawk > jsawk
RUN chmod +x jsawk && mv jsawk ~/bin/

# Get the latest minecraft server

# Set appropriate environment variables
# ENV JAVA_HOME=/MCSLI/java/jre1.8.0_25/
# ENV PATH=${PATH}:/MCSLI/java/jre1.8.0_25/bin