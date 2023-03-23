FROM amazoncorretto:17-al2-jdk AS system

# this is a terrible way of doing docker files.  The idea of docker is to
# have a system that was built and ran by devs repeatedly, so that by the time
# it gets to prod it's working exactly as it should.  Building outside of
# docker container system, and then running inside of it, does not do that.  We
# building/developing on different versions of java, a different OS, etc, etc.
#
VOLUME /tmp
ARG JAR_FILE=build/libs/takehome-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
CMD ["java","-jar","/app.jar"]