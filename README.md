# EZBag
### MVP 
Brick and mortar shopping scan-and-go checkout app

### User Experience Flow
- Scan a QR code at the store entrance to open the ezbag web app
- Enable camera permissions
- Scan any product's barcode (EAN, UPC) with my mobile phone camera
- Automatically present scanned product card on screen
  - Use plus/minus buttons to increase/decrease the item quanity on the item
  - Press down arrow button to go back to scanner view
  - Press the "Confirm" button to add the button to cart
- Press "Checkout" button Open cart view when pressing cart button in top right
  - Checkout via Apple Pay OR checkout via Google Pay
- Upon payment show the checked out view, displaying the customers unique cart number
  - Display input form for customer email and/or phone number to send digital receipt
    - Press the "Send" button to send the receipt to email/phone
  
## Technologies
### Backend
- Spring Boot
- Tomcat
- Jersey
- Maven
- MySQL

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
  - Hookup older work, setup new MySQL database, add initial 50 products
- Brendan Reidy
  - Get working backend "Hello World"
- Cody Shearer
  - Calls to the API
- Will Almond
  - Product card view
- Brendan Curran
  - Cart view & apple pay


