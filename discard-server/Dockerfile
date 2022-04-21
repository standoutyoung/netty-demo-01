# STEP 1: Setup
FROM openjdk:8-jdk-slim

WORKDIR app
ADD target/discard-server.jar discard-server.jar
#ADD libsapjco3.so $JAVA_HOME/jre/lib/amd64/server/libsapjco3.so
#ADD sapjco3.jar $JAVA_HOME/lib/sapjco3.jar
#设置时间为中国上海，默认为UTC时间
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT ["java","-jar","discard-server.jar"]
