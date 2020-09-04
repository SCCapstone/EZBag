# EZBag
### MVP 
Brick and mortar shopping scan-and-go checkout app

### User Experience Flow
- Scan a QR code at the store entrance to open the ezbag web app
- Enable camera permissions
- Scan any product's barcode (EAN, UPC) with my mobile phone camera
- Automatically present scanned product card on screen
  - --> Buzz when product scanned 
  - --> Lazily slide to increase the item quanity on the item popup
  - --> Cancel addition of product to cart via X button
  - --> 3 seconds of no product card interaction automatically adds item to user's cart
  - --> Allow scanning of products in background (behind card)
  - --> Upon scan of another product, remove old product card and present newly scanned product's card
- Open cart view when pressing cart button in top right
  - --> Checkout via Apple Pay
  - --> Checkout via Google Pay
- Display QR code digital receipt
- Display input form for email and/or phone number to send the digital receipt to the customer.
  
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


