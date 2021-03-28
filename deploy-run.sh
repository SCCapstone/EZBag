rm -rf /Library/Tomcat/webapps/EZBagWebapp
rm /Library/Tomcat/webapps/EZBagWebapp.war

cp /Users/blakeedwards/Documents/EZBag/EZBagWebapp/target/EZBagWebapp.war /Library/Tomcat/webapps

/Library/Tomcat/bin/./catalina.sh run

