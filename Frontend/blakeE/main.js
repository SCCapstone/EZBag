localStorage = window.localStorage
// TODO generate UUID if session does not already exist
if (localStorage.getItem("session") == null) {
    console.log("[INFO] Creating new session...")
    localStorage.setItem("session", "12345678");
} else {
    console.log("[INFO] Found existing session")
}

localStorage.setItem("businessID", "1");
// TODO check if cart already stored, if so, do not overwrite it
var newCart = {
    barcodes: [], 
    barcodeTypes: [],
    quantities: [],
    session: localStorage.getItem("session"),
    businessID: localStorage.getItem("businessID")
}
localStorage.setItem("cart", JSON.stringify(newCart));
console.log("Created new customer session: \n"+JSON.stringify(newCart))

function setCart(newCartArr) {
    localStorage.setItem("cart", JSON.stringify(newCartArr));
}

function getCart(){
    return JSON.parse(localStorage.getItem("cart"));
}

// TODO use to check if scanned product already in cart to set product card quantity field correctly 
function existsInCart(barcode, barcodeType) {
    if (getCart().barcodes.indexOf(barcode) > -1) {
        return true;
    }
    return false;
}

function addItemToCart(barcode, barcodeType, quantity) {
    var newCart = JSON.parse(localStorage.getItem("cart"));
    newCart.barcodes.push(barcode);
    newCart.barcodeTypes.push(barcodeType);
    newCart.quantities.push(quantity);
    setCart(newCart);
    console.log("[SUCCESS] Added item to cart")
}

function removeItemFromCart(barcode) {
    var newCart = getCart();
    var itemIdx = newCart.barcodes.indexOf(barcode);
    if (itemIdx > -1) {
        newCart.barcodes = newCart.barcodes.splice(itemIdx, 1);
        newCart.barcodeTypes = newCart.barcodeTypes.splice(itemIdx, 1);
        newCart.quantities = newCart.quantities.splice(itemIdx, 1);
        setCart(newCart);
        console.log("[SUCCESS] Item removed from cart")
    } else {
        console.log("[ERROR] Item not found in cart")
    }
}

console.log("Loading Barcode Scanner...")
const key = 'AQZOrRteBEkNL00Y2BzlDawGLF+INUj7V0OVEW4Lmj5oYTPF+kjyUjA3teTSf6JkdBp2jJxHosY9bJjU5kAHEgJ2TgfxJmP/3XKgn7ZIoW+laxrLzHsC4E1DGE9uePRx/Webii0FzQgYJij0agLqr+6v54OnkD6bUPbyAtVXf86RJHNkMlMkZDXyrOkAkbFz+6s7xAKEaXdsFOxxtB2I94VvbEtg/Y5DjumsqwuGs+uQEYgo40I28fAY04uegvVzhPJxy2Uajgod5qcda4Ou3zLkisEBZd8kFmGk2XWWfFR5eViTjiAxncRNmvrYABiF6jA78BVH70/H/Ca3JX3s7E/Y8VdyE+QUW4t5TVmPQA9Dl6WGYQmxWEB0TrRJ+upZBYQhkHBNDEnazyZpIRc4MjnSLmjh8E9fMOelewwDVh39mdo09asD/l7SJH8w1OQmPZ6df6QjvI8nZ8O2xdQNKZW6UrKAYF4lhTOohddaggikYQ5pxUp8WH+87fDiDo7VcRcBXPiGwCoRGvmc7GAibybhz0mJ50mX5h+5rqLNONo5iHCD8HGTv9FOIgaScV8/TkeX1rB49Kcbkp/8D5dQV0QiiqxCF1dNGKMmd75RKFjBnOyGvOo0QN3pALJ5L1Ug68vfVsLqa7OWPypnvNhRYrmWvdDPKoMoJqhpN5UJPX2M/Q1bgZlwxZeSGHlDFphJNXOoMDhH6uNFjbgwzLNfe0p8bXBmYUrsz0b9NsVIv2WqnSWE5/cf9+DmCbEMLo1lb8pvzFC6ZU31YME67X5To+BcdtE330WE+C6JFUAH9yv9s8YfYRMQwBNC9vvfx5A=';
ScanditSDK.configure(key, {
    engineLocation: "https://cdn.jsdelivr.net/npm/scandit-sdk@5.x/build/",
    })
    .then(() => {
        return ScanditSDK.BarcodePicker.create(document.getElementById("scandit-barcode-picker"), {
            playSoundOnScan: false,
            vibrateOnScan: true,
            videoFit: ScanditSDK.BarcodePicker.ObjectFit.COVER,
            scanSettings: new ScanditSDK.ScanSettings({ enabledSymbologies: ["ean8", "ean13", "upca", "upce"] }),
        });
    }).then((barcodePicker) => {
        // barcodePicker is ready here, show a message every time a barcode is scanned
        barcodePicker.on("scan", (scanResult) => {
            var barcode = scanResult.barcodes[0].data.toString(10)
            var symbology = scanResult.barcodes[0].symbology
            console.log("Scanned barcode")
            console.log(barcode)
            console.log(symbology)
            // TODO detect multiple scans of same product and prevent multiple lookup calls
            // TODO uncomment this when running with Tomcat
            // productLookup(barcode.toString(10), scanResult.barcodes[0].symbology, "1");
            mockProductLookup();
        }); 
});


// PRODUCT CARD CODE
var productQuantityDiv = document.getElementById("quantityText");
var scannedProductDiv = document.getElementById("scannedProductDiv")
var displayName = document.getElementById("nameText")
var displayPrice = document.getElementById("priceText")
var displayQuantity = document.getElementById("quantityText")

var downButton = document.getElementById("downButton")
var plusButton = document.getElementById("plusButton")
var minusButton = document.getElementById("minusButton")
var confirmButton = document.getElementById("confirmButton")
var maxNameLength = 15

let start;

downButton.onclick = function(){
    console.log("Cancel button pressed")
    productCardHide();
};

confirmButton.onclick = function(){
    console.log("Confirm button pressed")
    productCardHide();
 };

plusButton.onclick = function(){
    console.log("Plus button pressed")
    productQuantityDiv.innerHTML = parseInt(productQuantityDiv.innerHTML) + 1;
}

minusButton.onclick = function(){
    console.log("Minus button pressed")
    if(parseInt(productQuantityDiv.innerHTML) > 1)
    {
        productQuantityDiv.innerHTML = parseInt(productQuantityDiv.innerHTML) - 1;
    }
}

function displayItem()
{
    var resp = '{ "_id" : { "$oid" : "5f69529fda276d39ff5371d5" }, "barcode" : "9780061241895", "barcodeType" : "ean13", "name" : "Influence, The Psychology of Persuasion", "price" : 10.98, "description" : "National Best Seller. By Robert B. Cialdini, PH.D.", "businessID" : "1", "time" : { "$numberLong" : "1600737951212" } }'
    var item = JSON.parse(resp);
    console.log("Displaying product card...")
    price = item.price
    var nameLen = item.name.length
    if (nameLen > maxNameLength) {
        name = item.name.substring(0,maxNameLength).trim() + "..."
    } else {
        name = item.name
    }
    item["quantity"] = 1
    displayName.innerHTML = name
    displayPrice.innerHTML = "$" + price
    displayQuantity.innerHTML = item.quantity.toString()
    productCardShow();
    start = undefined
}


// function displayItem(item)
// {
//     console.log("Displaying product card...")
//     price = item.price
//     var nameLen = item.name.length
//     if (nameLen > maxNameLength) {
//         name = item.name.substring(0,maxNameLength).trim() + "..."
//     } else {
//         name = item.name
//     }
//     item["quantity"] = 1
//     displayName.innerHTML = name
//     displayPrice.innerHTML = "$" + price
//     displayQuantity.innerHTML = item.quantity.toString()
//     quantityText = item
//     productCardShow();
//     start = undefined
// }

function productCardShow() {
    scannedProductDiv.style.display = "block";
}

function productCardHide() {
    scannedProductDiv.style.display = "none";
}
// END - PRODUCT CARD CODE


// PRODUCT SCANNER CODE
var quantityText = null;
var checkoutBtn = document.getElementById("checkoutBtn")


checkoutBtn.onclick = function(){
   console.log("Moving to checkout cart view...")
};

function mockProductLookup() {
    var resp = '{ "_id" : { "$oid" : "5f69529fda276d39ff5371d5" }, "barcode" : "9780061241895", "barcodeType" : "ean13", "name" : "Influence, The Psychology of Persuasion", "price" : 10.98, "description" : "National Best Seller. By Robert B. Cialdini, PH.D.", "businessID" : "1", "time" : { "$numberLong" : "1600737951212" } }'
    var returnedProduct = JSON.parse(resp);
    displayItem(returnedProduct)
}

var productLookup = function(code, codeType, bizID) {   
    queryObj = {
                barcode: code,
                barcodeType : codeType,
                businessID: bizID
               }
    console.log(queryObj)
    var aUrl = "http://localhost:8080/EZBagWebapp/webapi/lookup";
    var xhr = $.ajax({
        type: "POST",
        data: JSON.stringify(queryObj),
        location: "",
        url: aUrl, 
        success: function(output, status) {
            alert(output);
	    // TODO call displayProductCard function and pass utilized fields
            console.log(output);
            $('.sysMsg').html(output);
        },
        error: function(output) {
            $('.sysMsg').html(output);
            console.log(JSON.stringify(queryObj))
            console.log(output)
	        console.log("[ERROR] product lookup")
        }
    });
  };

var displayProductCard = new function() {
    // TODO implement this method
    console.log("Displaying product card view")
}

// TODO need helper function to call cust. event post
var customerEventPost = function(eventType, sessionID, productBarcode) {
    eventObj = {
    	type: eventType,
	session: sessionID,
	barcode: productBarcode
    }
    var eventRoute = "http://localhost:8080/EZBagWebapp/webapi/event";
    postRequest(JSON.stringify(eventObj), eventRoute);
};

var postRequest = function(payload, route) {
    var xhr = $.ajax({
        type: "POST",
        data: payload,
        location: "",
        url: route, 
        success: function(output, status) { 
            console.log("[SUCCESS] HTTP post request")
            $('.sysMsg').html(output);
        },
        error: function(output) {
	    console.log("[ERROR] HTTP post request failed")
            $('.sysMsg').html(output);
        }
    });
  };
productCardHide();
// END - PRODUCT SCANNER CODE
