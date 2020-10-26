<html>
    <head>
        <script src="https://cdn.jsdelivr.net/npm/scandit-sdk@5.x"></script>
        <script src="https://kit.fontawesome.com/8b57238d0d.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="css/scanner.css">
    </head>
    <body>

        <!--PRODUCT SCANNER ELEMENT-->
        <div class="scanner" id="scandit-barcode-picker"></div>
        <div id="checkoutBtn" class='btn-bot-left'>
            <div class="inner text scannerButtonText">
                Checkout
            </div>
        </div>
        <div class='btn-bot-right'>
            <div class="inner text scannerButtonText" id="subtotalButton">
                $0.00
            </div>
        </div>
        <!--END - PRODUCT SCANNER ELEMENT-->

        <!--PRODUCT CARD ELEMENT-->
        <div id="scannedProductDiv" style="display: none;">
            <button class="roundButton button" id="downButton">
                <i id="downIcon" class="fas fa-chevron-down buttonIcon"></i>
            </button>
            <div id="nameText" class="centerText text">
                Iodized Salt
            </div>
            <div id="priceText" class="centerText text">
                $10.99
            </div>
            <button class="roundButton button" id="plusButton">
                <i id="plusIcon" class="fas fa-plus buttonIcon"></i>
            </button>
            <div class="centerText text" id="quantityText">
                1
            </div>
            <button class="roundButton button" id="minusButton">
                <i id="minusIcon" class="fas fa-minus buttonIcon"></i>
            </button>
            <button class="ovalButton button text" id="confirmButton">
                Confirm
            </button>
        </div>
        <!--END - PRODUCT CARD ELEMENT-->
    </body>
    <script src="scripts/cart.js"></script>
    <script src="scripts/scanner.js"></script>
</html>
