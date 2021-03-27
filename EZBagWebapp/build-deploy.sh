#!/bin/bash
rm -rf /usr/local/tomcat/webapps/* && # remove old backend deployed in tomcat
mvn -f /backend/pom.xml clean package && # build backend from source
mv /backend/target/EZBagWebapp.war /usr/local/tomcat/webapps # deploy backend with tomcat