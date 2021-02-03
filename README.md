# EZBag

Scan-and-go is a form of self-checkout which has recently been growing in popularity with big-box stores. 
However, custom software is expensive and smaller retailers do not have the budget to develop their own custom scan-and-go solution.
Therefore, EZBag is a free web app that aims to allow any brick-and-mortar store to implement scan-and-go. 
A patron at a participating store can use the EZBag web app on their phone to scan and pay for items without going through the typical checkout process.
[EZBag web app view mockups can be found here.](https://github.com/SCCapstone/EZBag/wiki/Requirements)


## [EZBag Proof of Concept Presentation & Demo](https://youtu.be/l9hTREqO-ws)
Click this link to view our proof of concept presentation video on YouTube

## [Proof of Concept Web App](https://blakeedwards.me:8443/EZBagWebapp/#/)
Click this link to view our proof of concept application. NOTE: Our website uses a self-signed SSL certificate and the best platform to use the EZBag app on is iPhone with the Safari browser. When opening the web application link if you you are presented with a "Your connection is not private" message (or something similar), override and continue to the site anyways. To do this, we recommend using Safari (as Google Chrome does not allow you to override and continue to the page). Also note that this application is built for a mobile web browser and therefore will not display correctly on a desktop view. If you would like to view the app on your desktop computer, we recommend opening this link in Safari and changing to Responsive Design Mode ("Develop" menu item > "Enter Responsive Design Mode").

## Instructions to use the demo
1. [Open the web app](https://blakeedwards.me:8443/EZBagWebapp/#/)
2. Allow camera access to the web app
3. Scan the barcode below (Note: unknown products can be scanned, and they will appear in the cart with an example name, but they won't be included in the digital reciept).

  ![](https://github.com/SCCapstone/EZBag/blob/master/readme/barcode_example.jpg)

4. Navigate to the "Cart" tab
5. Press the "$" button to pay
6. Press the "Checkout" button to mock pay
7. Put in a standard 10 digit phone number to receive your digital receipt (email sending works locally but not yet on server deployment at this time)


## Setup, Running/Deployment, Testing
This project is split into a frontend and a backend, so we will provide instructions for each.

### Setup Backend

Install the following requirements:
* [Java 1.8](https://www.oracle.com/java/technologies/javase-downloads.html)
* [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows)
* [Tomcat 8](https://tomcat.apache.org/)
    * Download [Windows Service Installer](https://tomcat.apache.org/download-80.cgi)
    * Run it and follow the prompts for installation
* [MongoDB](https://www.mongodb.com/)
    * [Official Install Instructions](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/)

Download or `git clone` this repository.

Open the `EZBagWebapp` folder in IntelliJ. The Maven plugin built into IntelliJ will download and auto-configure all of the project dependencies.

### Build and Deploy Backend
Open the EZBagWebapp project in IntelliJ

Open Maven side menu

- Open Lifecycle menu
    - clean
    - compile
    - package

Open "target" folder in project root

Copy "EZBagWebapp.war" to the ```/path-to-tomcat-install/tomcat/webapps``` directory

Start Tomcat and navigate to `/path-to-tomcat-install/tomcat/bin/`

Run `startup.sh`/`startup.bat` if your on (linux/windows)

Tomcat will now auto-deploy the application which can be viewed at http://localhost:8080/EZBagWebapp.

### Backend Unit Testing
Using the [Jersey Test Framework](https://www.baeldung.com/jersey-test), unit tests can be ran in the IDE (IntelliJ). The Jersey Test Framework creates a fast and easy way to quickly test Jersey code. The Test Framework can emulate GET, and POST requests, and verify that the values being returned are correct. Can also be used to test any backend methods without deploying the server via TomCat.

Tests location: EZBagWebapp/src/main/java/test

Running the tests in the Jersey Test Framework is easy:
1. Open the EZBagWebapp/src/main/java/test package 
2. Right click on the test you want to run and click "run test"
3. To run the multiple tests in the test package you can simply right click on the containing package and click "run all tests".

### Setup Frontend
Download or `git clone` this repository if you haven't already.
Install [`npm`](https://www.npmjs.com/get-npm).
Open a terminal in `repository/Frontend/customer` and run `npm install` to install all dependencies.

### Build and Run Frontend
To build the frontend, open a terminal in `repository/Frontend/customer` and run `npm run build`. The build will be placed in `repository/Frontend/customer/dist`.

To run a local development build of the frontend at localhost:9000/, open a terminal in `repository/Frontend/customer` and run `npm run serve` 

### Frontend Behavioral Testing
We use [Cypress](https://www.cypress.io/) for end-to-end behavioral testing. 

Tests location: /Frontend/customer/tests/

1. If you haven't already, install `npm` and run `npm install` in the /Frontend/customer/ directory to install all dependencies for building/testing the frontend.
2. Then, simply run `npm run test:e2e`. Tests are located in /Frontend/customer/tests/, with a folder structure similar to the [what is described here](https://docs.cypress.io/guides/core-concepts/writing-and-organizing-tests.html#Folder-Structure).
3. A window will open displaying our tests. Select the test to run, or select 'run all tests'.


# Authors
- [Blake Edwards](mailto:blakete@email.sc.edu)
- [Brendan Reidy](mailto:bcreidy@email.sc.edu)
- [Cody Shearer](mailto:shearerc@email.sc.edu)
- [Will Almond](mailto:cwalmond@email.sc.edu)
- [Brendan Curran](mailto:bcurran@email.sc.edu)
