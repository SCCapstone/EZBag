[![Stable](https://img.shields.io/badge/status-stable-green.svg)](https://github.com/blakete/perdisco) [![MIT License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)

# EZBag

## Demo of Customer Checkout Experience

Click the thumbnail below to watch the demo of customer checkout on YouTube.

<a href="https://youtu.be/MeghahEn6Ho?si=3H-ht-jx-xrI3D9s&t=305">
  <img src="readme/EZBag_Customer_Checkout_Flow_Thumbnail.png" alt="EZBag Customer Checkout" width="480" height="360">
</a>

## Demo of Merchant Side Experience

Click the thumbnail below to watch the demo of the merchant side of the EZBag web app on YouTube.

<a href="https://youtu.be/MeghahEn6Ho?si=vBvII5FRAg2O7pdi&t=366">
  <img src="readme/EZBag_Merchant_Side_Demo_Thumbnail.png" alt="EZBag Merchant Side Demo" width="480" height="360">
</a>

## Final Presentation

Click the thumbnail below to watch the final presentation video on YouTube.

[![EZBag Final Presentation](https://img.youtube.com/vi/MeghahEn6Ho/hqdefault.jpg)](https://youtu.be/MeghahEn6Ho)

---

## Project Description

Scan-and-go is a form of self-checkout which has recently been growing in popularity with big-box stores. 
However, custom software is expensive and smaller retailers do not have the budget to develop their own custom scan-and-go solution.
Therefore, EZBag is a free web app that aims to allow any brick-and-mortar store to implement scan-and-go. 
A patron at a participating store can use the EZBag web app on their phone to scan and pay for items without going through the typical checkout process.
[Click here for an online demo](https://www.blakeedwards.me/EZBagWebapp/#/)

## Initial Setup with Docker:
1. Install docker (if you are on Windows 10, use WSL2 for docker).
2. Clone the repository and cd into it
4. Run `docker-compose build`
3. Run `docker-compose run --rm --name backend backend`. You will be prompted for a password, which we will supply.
    - Note: We need to use `docker-compose run ...` on the first startup for its interactive mode, without which you could not type in the password.
4. While the backend appears to be fully running, ports are not properly mapped when running with `docker-compose run ...` . Close the containers with `docker-compose down`.

## Usage
Having completed the inital setup with docker, our project can be started with `docker-compose up`. The frontend will be served in development mode at http://localhost:9000, the backend will be served by tomcat on http://localhost:8080/EZBagWebapp, and our mongodb database will be available on port 27017 of localhost. Cypress will run behavioral tests once http://localhost:9000 becomes available, and it will exit after either the tests finish or if a test fails. When you are finished with the project, these three services should be closed with `docker-compose down`. 

## Rebuild and Redeploy the Backend:
If not already deployed, `docker-compose up` will build the backend with maven and deploy it with tomcat. If you need to rebuild the backend (say, if you change the source code):
1. Run `docker-compose up` in this repository
2. In another terminal, run `docker exec -it backend bash` to access the backend container.
3. In the backend container and at `/backend` run `./build-deploy.sh`
The backend will be rebuilt and automatically deployed to `/usr/local/tomcat/webapps`. However, through the magic of [docker volumes](https://docs.docker.com/storage/volumes/), it will also be available on your local machine at `this_repository/docker_persist/tomcat/`. 

## Build the Frontend
The frontend is automatically served at http://localhost:9000 in development mode when you use `docker-compose up`. However, if you need to build the frontend for production, you can:
1. If the project is not already running in docker, run `docker-compose up`.
2. Access the running frontend container in another terminal with `docker exec -it backend bash`
3. In the frontend container and at `/frontend`, run `npm run build`
The frontend will be built and placed at `/frontend/dist`. However, through the magic of [docker volumes](https://docs.docker.com/storage/volumes/), it will also be available on your local machine at `this_repository/Frontend/dist`. 

# Testing
## Backend Unit Testing:
Using the [Jersey Test Framework](https://www.baeldung.com/jersey-test), unit tests can be ran in the IDE (IntelliJ). The Jersey Test Framework creates a fast and easy way to quickly test Jersey code. The Test Framework can emulate GET, and POST requests, and verify that the values being returned are correct. Can also be used to test any backend methods without deploying the server via TomCat.

Tests location: `EZBagWebapp/src/main/java/test`

Running the tests in the Jersey Test Framework:
1. Open the EZBagWebapp/src/main/java/test package 
2. Right click on the test you want to run and click "run test"
3. To run the multiple tests in the test package you can simply right click on the containing package and click "run all tests".

## Frontend Behavioral Testing:
We use [Cypress](https://www.cypress.io/) for end-to-end behavioral testing. Tests are located in /e2e/cypress/integration/, with a folder structure similar to the [what is described here](https://docs.cypress.io/guides/core-concepts/writing-and-organizing-tests.html#Folder-Structure).

Tests are automatically run in the background with `docker-compose up`. Screenshots and videos of failed tests will be placed in e2e/cypress/.

### Run tests interactively (GUI):
0. You'll need to setup an xserver on your host machine (google it, you're on your own for this one).
1. Get the IP address of your host machine and allow X11 to accept incoming connections from that IP address (if you are using VcXsrv, you can _disable access control_ to allow all incoming connections).
2. Set the environment variable DISPLAY using `export DISPLAY=$(cat /etc/resolv.conf | grep nameserver | awk '{print $2; exit;}'):0.0`
    - I added this to my .bashrc
    - If you have trouble with this command, try `IP=$(ipconfig getifaddr en0)` then `DISPLAY=$IP:0`
3. Having completed the preceding steps, you can start interactive tests at any time using `docker-compose -f docker-compose.yml -f e2e/cy-open.yml up --exit-code-from cypress`.
    - You can even run this after `docker-compose up`. There is NO need to `docker-compose down` - it would just take longer to get running.

# User Flow
## Merchant Flow
- On the login page, you can login to an existing account or if you are new to EZBag you can create an account by clicking "Sign up".
- The "Sign up" button will redirect you to the registration page where you will enter in business information. Once you submit the information, you get a link sent to your email to verify the account. After you verify your account, you will be redirected back to the login page and a QR code for customer use will be sent to your email.
- Upon login, you will be able to see recently checked out carts and their contents as well as click "Verify" to confirm that the cart is valid and paid for.
- Merchant can also scan items, enter in their product information, and add them to the database if it is not in the database. You can also scan existing products and edit or delete them.
- The Sale Dashboard will show a chart of the sales broken down by different time periods.

## Customer Flow
- To get to the customer flow, you must scan the QR code that was sent to the merchant email address.
- Once you reach the homepage of the EZBag webapp, you can scan and search items. When you scan or search an item, a menu will popup where you can edit the quantity of this item and add it to your cart, or cancel out of menu to scan a different item.
- The cart will display all the items the customer has added to their cart as well as the total. To checkout, you can click the "$" button at the bottom of the page.
- You will be redirected to the receipt page which will show your cart number and allow you to enter you email address or phone number. Once you press the "Confirm" button, you will receive a email and/or text receipt.

# Authors
- [Blake Edwards](mailto:blakete@email.sc.edu)
- [Brendan Reidy](mailto:bcreidy@email.sc.edu)
- [Cody Shearer](mailto:shearerc@email.sc.edu)
- [Will Almond](mailto:cwalmond@email.sc.edu)
- [Brendan Curran](mailto:bcurran@email.sc.edu)
