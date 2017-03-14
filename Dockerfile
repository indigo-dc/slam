FROM ubuntu:16.04

ENV PROVIDER_EMAIL ''
ENV IAM_TOKEN_URL 'https://iam-test.indigo-datacloud.eu/token'
ENV IAM_URL 'https://iam-test.indigo-datacloud.eu'
ENV IAM_CLIENT_ID '53b937c5-fd53-4626-9400-8b21838d7de2'
ENV IAM_CLIENT_SECRET 'fcqrPQYZtR-CETMdvxrNoQAKtDw-Qy8hb0ItHd4cX6IJpi6f7124YDYV8SAuL99KfkWMQchQalBP2fJAlhRB7Q'
ENV CMDB_URL 'http://indigo.cloud.plgrid.pl'
ENV MYSQL_ADDRESS 'mysql'
ENV MYSQL_PORT '3306'
ENV MYSQL_DB 'indigo_slam'
ENV MYSQL_USER 'root'
ENV MYSQL_PASSWORD 'root'
ENV MONGO_ADDRESS 'mongo'
ENV MONGO_PORT '27017'
ENV MONGO_DB 'indigo_slam_engine'
ENV MONGO_USER 'engine'
ENV MONGO_PASSWORD ''
ENV CERT_ALIAS 'root'
ENV CERT_FILE ''
ENV KEYSTORE_PASSWORD 'helpdesk'
ENV KEYSTORE '/opt/pki/synchroDuo.jks'
ENV KEYSTORE_ALIAS 'synchro'

RUN rm /bin/sh && ln -s /bin/bash /bin/sh

RUN apt-get -y update
RUN apt-get install -y openjdk-8-jre-headless

RUN mkdir -p /opt/indigo-slam
COPY target/*.jar /opt/indigo-slam/
COPY docker/pki/synchroDuo.jks /opt/pki/
COPY docker/indigo-slam.sh /opt/indigo-slam/

RUN ln -s /opt/indigo-slam/indigo-slam.sh /usr/bin/indigo-slam.sh

VOLUME /opt/certs
VOLUME /opt/pki/custom

EXPOSE 8443

ENTRYPOINT ["indigo-slam.sh"]
CMD [""]
