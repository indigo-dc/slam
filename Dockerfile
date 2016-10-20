FROM ubuntu:16.04
RUN rm /bin/sh && ln -s /bin/bash /bin/sh

RUN apt-get -y update
RUN apt-get install -y openjdk-8-jre
RUN apt-get install -y mongodb
RUN echo "mysql-server mysql-server/root_password password root" |  debconf-set-selections
RUN echo "mysql-server mysql-server/root_password_again password root" | debconf-set-selections
RUN apt-get install -y mysql-server

RUN mkdir -p /opt/indigo-slam
COPY target/*.jar /opt/indigo-slam/
COPY docker/pki/synchroDuo.jks /opt/pki/

EXPOSE 8443

#ENTRYPOINT ["java -jar /opt/indigo-slam/*.jar -Dspring.cloud.config.username=developer -Dspring.cloud.config.password=developer -Dspring.profiles.active=development"]
#CMD [""]