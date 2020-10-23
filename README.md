
# EZBag Project


Scan-and-go is a form of self-checkout which has recently been growing in popularity with big-box stores. 
However, custom software is expensive and smaller retailers do not have the budget to develop their own custom scan-and-go solution.
Therefore, EZBag is a free web app that aims to allow any brick-and-mortar store to implement scan-and-go. 
A patron at a participating store can use the EZBag web app on their phone to scan and pay for items without going through the typical checkout process.


[EZBag web app view mockups can be found here.](https://github.com/SCCapstone/EZBag/wiki/Requirements)


## External Requirements


* [Java 1.8](https://www.oracle.com/java/technologies/javase-downloads.html)
* [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows)
* [Tomcat 8](https://tomcat.apache.org/)
    * Download [windows service installer](https://tomcat.apache.org/download-80.cgi)
    * Run it and follow the prompts for installation
* [MongoDB](https://www.mongodb.com/)
    * [Official Install Instructions](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/)




## Setup


- Download this repository
```
git clone https://github.com/SCCapstone/m3-blakete.git
```
- Open the EZBagWebapp project in IntelliJ
    - The Maven plugin built into IntelliJ will setup the project and all of it dependencies for you


## Running


- Open the EZBagWebapp project in IntelliJ
- Open Maven side menu
    - Open Lifecycle menu
        - clean
        - compile
        - package
- Open "target" folder in project root
- Copy "EZBagWebapp.war" to the ```/path-to-tomcat-install/tomcat/webapps``` directory
- Start Tomcat
```
/path-to-tomcat-install/tomcat/bin/startup.sh
```
- Tomcat will now auto-deploy the application which can be view at http://localhost:8080/EZBagWebapp 


# Deployment


- Perform all steps for running up until “Copy…”
- Secure copy the "EZBagWebapp.war" to your remote server
- Copy the "EZBagWebapp.war" to the ```/path-to-tomcat-install/tomcat/webapps``` directory
- Start tomcat on remote server
```
/path-to-tomcat-install/tomcat/bin/startup.sh
```
- Tomcat will now auto-deploy the application which can be view at http://yourdomain.com:8080/EZBagWebapp 






# Testing


CSCE 492 we will write automated tests. When done, an explanation of usage will be here.


## Testing Technology


TODO: Jersey Test Framework


## Running Tests


ExplainWhen done, an explanation of  how to run the automated tests will be here.




# Authors


- [Blake Edwards](mailto:blakete@email.sc.edu)
- [Brendan Reidy](mailto:bcreidy@email.sc.edu)
- [Cody Shearer](mailto:shearerc@email.sc.edu)
- [Will Almond](mailto:cwalmond@email.sc.edu)
- [Brendan Curran](mailto:bcurran@email.sc.edu)
