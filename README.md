
# EZBag Project

## [Proof of Concept Demo Video](https://youtube.com)
Click this link to view our proof of concept presentation video on YouTube

## [Proof of Concept Web App](https://blakeedwards.me:8443/EZBagWebapp/#/)
Click this link to view our proof of concept application. NOTE: Our website uses a self-signed SSL certificate and the best platform to use the EZBag app on is iPhone with the Safari browser. When opening the web application link if you you are presented with a "Your connection is not private" message (or something similar), override and continue to the site anyways. To do this, we recommend using Safari (as Google Chrome does not allow you to override and continue to the page). Also note that this application is built for a mobile web browser and therefore will not display correctly on a desktop view. If you would like to view the app on your desktop computer, we recommend opening this link in Safari and changing to Responsive Design Mode ("Develop" menu item > "Enter Responsive Design Mode").

## Instructions to use the demo
1. [Open the web app](https://blakeedwards.me:8443/EZBagWebapp/#/)
2. Allow camera access to the web app
3. Scan the barcode below
4. Navigate to the "Cart" tab
5. Press the "$" button to pay
6. Press the "Checkout" button to mock pay
7. Put in a standard 10 digit phone number to receive your digital receipt (email sending works locally but not yet on server deployment at this time)

## What is EZBag?

Scan-and-go is a form of self-checkout which has recently been growing in popularity with big-box stores. 
However, custom software is expensive and smaller retailers do not have the budget to develop their own custom scan-and-go solution.
Therefore, EZBag is a free web app that aims to allow any brick-and-mortar store to implement scan-and-go. 
A patron at a participating store can use the EZBag web app on their phone to scan and pay for items without going through the typical checkout process.


[EZBag web app view mockups can be found here.](https://github.com/SCCapstone/EZBag/wiki/Requirements)


## External Requirements


* [Java 1.8](https://www.oracle.com/java/technologies/javase-downloads.html)
* [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows)
* [Tomcat 8](https://tomcat.apache.org/)
    * Download [Windows Service Installer](https://tomcat.apache.org/download-80.cgi)
    * Run it and follow the prompts for installation
* [MongoDB](https://www.mongodb.com/)
    * [Official Install Instructions](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/)


## Setup


- Download this repository
```
git clone https://github.com/SCCapstone/EZBag.git
```
- Open the EZBagWebapp project in IntelliJ
    - The Maven plugin built into IntelliJ will download and auto-configure all of the project dependencies for you


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
/path-to-tomcat-install/tomcat/bin/./startup.sh
```
- Tomcat will now auto-deploy the application which can be view at http://localhost:8080/EZBagWebapp 


# Deployment


- Perform all steps for running up until “Copy…”
- Secure copy the "EZBagWebapp.war" to your remote server
- Copy the "EZBagWebapp.war" to the ```/path-to-tomcat-install/tomcat/webapps``` directory
- Start tomcat on remote server
```
/path-to-tomcat-install/tomcat/bin/./startup.sh
```
- Tomcat will now auto-deploy the application which can be view at http://yourdomain.com:8080/EZBagWebapp 






# Testing


CSCE 492 we will write automated tests. When done, an explanation of usage will be here.


## Testing Technology


TODO: Jersey Test Framework


## Running Tests


TODO: Explain how to run the automated tests will be here.




# Authors


- [Blake Edwards](mailto:blakete@email.sc.edu)
- [Brendan Reidy](mailto:bcreidy@email.sc.edu)
- [Cody Shearer](mailto:shearerc@email.sc.edu)
- [Will Almond](mailto:cwalmond@email.sc.edu)
- [Brendan Curran](mailto:bcurran@email.sc.edu)
