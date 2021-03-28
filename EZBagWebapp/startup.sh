#!/bin/bash
# deploy secrets if not already deployed
if [ ! -d /usr/local/opt/EZBag ]; then
  gpg --decrypt -o /backend/secrets.tar.gz /backend/secrets.tar.gz.gpg &&
  tar -xzvf /backend/secrets.tar.gz &&
  rm /backend/secrets.tar.gz  &&
  mkdir -p /usr/local/opt  &&
  mv EZBag /usr/local/opt
fi

# deploy webapp in not already deployed
if [ ! -f /usr/local/tomcat/webapps/EZBagWebapp.war ]; then
  # only build webapp if not already built
  if [ ! -f /backend/target/EZBagWebapp.war ]; then
    /backend/./build-deploy.sh
  else # just deploy
    mv /backend/target/EZBagWebapp.war /usr/local/tomcat/webapps
  fi
fi

# run tomcat
#exec /usr/local/tomcat/bin/catalina.sh $1
exec catalina.sh $1