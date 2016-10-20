#!/bin/bash
service mongodb start
service mysql start
java -Dserver.address=0.0.0.0 -Dunity.server.token=$IAM_TOKEN_URL -Dunity.server.base=$IAM_URL -Dcmdb.url=$CMDB_URL -Dprovider.email=$PROVIDER_EMAIL -Dspring.cloud.config.username=developer -Dspring.cloud.config.password=developer -jar /opt/indigo-slam/gs-accessing-data-rest-0.1.0.jar