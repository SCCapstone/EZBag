# EZBag
### MVP 
Brick and mortar shopping scan-and-go checkout app

### User Experience Flow
- Scan a QR code at the store entrance to open the EZBag web app
- Enable camera permissions
- Scan any product's barcode (EAN, UPC) with my mobile phone camera
- Automatically present scanned product card on screen
  - Use plus/minus buttons to increase/decrease the item quantity on the item
  - Press down arrow button to go back to scanner view
  - Press the "Confirm" button to add the button to cart
- Press "Checkout" button open cart view when pressing cart button in top right
  - Checkout via Apple Pay OR checkout via Google Pay
- Upon payment show the checked out view, displaying the customers unique cart number
  - Display input form for customer email and/or phone number to send digital receipt
    - Press the "Send" button to send the receipt to email/phone
  
## Technologies
### Backend
- Tomcat
- Jersey
- Maven
- MongoDB

### Frontend
- HTML/CSS
- Vanilla Javascript
- JQuery for async web requests

## Dependencies
- Apache Tomcat [here](https://tomcat.apache.org/download-70.cgi) (Used for deploying server)
- I recommend you use IntelliJ and VSCode at least at first so we can get everything working easily on everyones computer
- Jersey maven should install automatically if you have IntelliJ

## Contributers
- Blake Edwards
- Brendan Reidy
- Cody Shearer
- Will Almond
- Brendan Curran



# EZBag Project

EZBag is a software as a web application that allows any brick-and-mortar store to implement scan-and-go. 
Scan-and-go is a form of self-checkout which has recently been growing in popularity with big-box stores. 
A patron at a participating store can use the EZBag web app on their phone to scan and pay for items without going through the typical checkout process.


Your audience for the Readme.md are other developers who are joining your team.
Specifically, the file should contain detailed instructions that any developer
can follow to install, compile, run, and test your project. These are not only
useful to new developers, but also to you when you have to re-install everything
because your old laptop crashed. Also, the teachers of this class will be
following your instructions.

## External Requirements

List all the stuff the reader will need to install in order to get you app to 
run in their laptop. For example:

In order to build this project you first have to install:

* [Node.js](https://nodejs.org/en/)
* [MongoDB](https://www.mongodb.com/)
    * asdasd

If possible, list the actual commands you used to install these, so the reader
can just cut-n-paste the commands and get everything setup.

You only need to add instructions for the OS you are using.

## Setup

Here you list all the one-time things the developer needs to do after cloning
your repo. Sometimes there is no need for this section, but some apps require
some first-time configuration from the developer, for example: setting up a
database for running your webapp locally.

## Running

Specify the commands for a developer to run the app from the cloned repo.

# Deployment

Webapps need a deployment section that explains how to get it deployed on the 
Internet. These should be detailed enough so anyone can re-deploy if needed
. Note that you **do not put passwords in git**. 

Mobile apps will also sometimes need some instructions on how to build a
"release" version, maybe how to sign it, and how to run that binary in an
emulator or in a physical phone.

# Testing

In 492 you will write automated tests. When you do you will need to add a 
section that explains how to run them.

The unit tests are in `/test/unit`.

The behavioral tests are in `/test/casper/`.

## Testing Technology

In some cases you need to install test runners, etc. Explain how.

## Running Tests

Explain how to run the automated tests.

# Authors

Your names and emails

