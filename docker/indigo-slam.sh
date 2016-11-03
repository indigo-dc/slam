#!/bin/bash
JDBC_URL="jdbc:mysql://$MYSQL_ADDRESS:$MYSQL_PORT/$MYSQL_DB?createDatabaseIfNotExist=true&useUnicode=true&amp;characterEncoding=UTF-8&amp;autoReconnect=true&amp;autoReconnectForPools=true"



java -Dserver.address=0.0.0.0 \
     -Dunity.server.token=$IAM_TOKEN_URL \
     -Dunity.server.base=$IAM_URL \
     -Dcmdb.url=$CMDB_URL \
     -Dprovider.email=$PROVIDER_EMAIL \
     -Djdbc.url=$JDBC_URL \
     -Djdbc.username=$MYSQL_USER \
     -Djdbc.password=$MYSQL_PASSWORD \
     -Dengine.mongodb.host=$MONGO_ADDRESS \
     -Dengine.mongodb.port=$MONGO_PORT \
     -Dengine.mongodb.user=$MONGO_USER \
     -Dengine.mongodb.pass=$MONGO_PASSWORD \
     -Dengine.mongodb.db=$MONGO_DB \
     -Dspring.cloud.config.username=developer \
     -Dspring.cloud.config.password=developer \
     $@ \
     -jar /opt/indigo-slam/gs-accessing-data-rest-0.1.0.jar