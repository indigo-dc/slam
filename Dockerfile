FROM ubuntu:16.04

ENV PROVIDER_EMAIL ''
ENV IAM_TOKEN_URL='https://iam-test.indigo-datacloud.eu/token'
ENV IAM_URL='https://iam-test.indigo-datacloud.eu'
ENV CMDB_URL 'http://indigo.cloud.plgrid.pl'

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
COPY docker/indigo-slam.sh /opt/indigo-slam/

COPY docker/my.cnf /etc/mysql/my.cnf
COPY docker/mongodb.conf /etc/mongodb.conf

RUN ln -s /opt/indigo-slam/indigo-slam.sh /usr/bin/indigo-slam.sh

VOLUME /var/lib/mysql
VOLUME /var/lib/mongodb

EXPOSE 8443

ENTRYPOINT ["indigo-slam.sh"]
CMD [""]