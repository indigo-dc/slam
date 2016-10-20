#!/bin/bash
java -Dspring.cloud.config.username=developer -Dspring.cloud.config.password=developer $@ -jar /opt/indigo-slam/gs-accessing-data-rest-0.1.0.jar