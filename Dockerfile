FROM openjdk:11
ARG OPTIONS="-XX:InitialRAMPercentage=50 -XX:MaxRAMPercentage=80 -XX:+PrintCommandLineFlags -XshowSettings:vm"
ENV OPT=$OPTIONS
WORKDIR /opt/app
COPY target/tasks-tracker-*.jar tasks-tracker.jar
CMD java ${OPT} -jar tasks-tracker.jar
