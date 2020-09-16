<html>
    <head>
        <script src="https://cdn.jsdelivr.net/npm/scandit-sdk@5.x"></script>
        <style>
            div.absolute {
              height: 30%;
              position: absolute;
              bottom: 0%;
              width: 100%;
              background-color: #393838;
              opacity: 1;
            }

            .button {
              background-color: #AFAFAF;
              border: none;
              color: white;
              padding: 20px;
              text-align: center;
              text-decoration: none;
              display: inline-block;
              font-size: 16px;
              margin: 4px 2px;
              cursor: pointer;
            }

            .button1 {border-radius: 50%;}

        </style>
    </head>
    <body>
        <button class="button button1">Bag</button>
        <div class="absolute">This div element has position: absolute;</div>
        <div class="scanner" id="scandit-barcode-picker"></div>
    </body>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="scripts/exampleScript.js"></script>
</html>
