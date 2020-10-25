// setup local storage object
localStorage = window.localStorage


console.log("[INFO] Loading Barcode Scanner...")
const key = 'AQZOrRteBEkNL00Y2BzlDawGLF+INUj7V0OVEW4Lmj5oYTPF+kjyUjA3teTSf6JkdBp2jJxHosY9bJjU5kAHEgJ2TgfxJmP/3XKgn7ZIoW+laxrLzHsC4E1DGE9uePRx/Webii0FzQgYJij0agLqr+6v54OnkD6bUPbyAtVXf86RJHNkMlMkZDXyrOkAkbFz+6s7xAKEaXdsFOxxtB2I94VvbEtg/Y5DjumsqwuGs+uQEYgo40I28fAY04uegvVzhPJxy2Uajgod5qcda4Ou3zLkisEBZd8kFmGk2XWWfFR5eViTjiAxncRNmvrYABiF6jA78BVH70/H/Ca3JX3s7E/Y8VdyE+QUW4t5TVmPQA9Dl6WGYQmxWEB0TrRJ+upZBYQhkHBNDEnazyZpIRc4MjnSLmjh8E9fMOelewwDVh39mdo09asD/l7SJH8w1OQmPZ6df6QjvI8nZ8O2xdQNKZW6UrKAYF4lhTOohddaggikYQ5pxUp8WH+87fDiDo7VcRcBXPiGwCoRGvmc7GAibybhz0mJ50mX5h+5rqLNONo5iHCD8HGTv9FOIgaScV8/TkeX1rB49Kcbkp/8D5dQV0QiiqxCF1dNGKMmd75RKFjBnOyGvOo0QN3pALJ5L1Ug68vfVsLqa7OWPypnvNhRYrmWvdDPKoMoJqhpN5UJPX2M/Q1bgZlwxZeSGHlDFphJNXOoMDhH6uNFjbgwzLNfe0p8bXBmYUrsz0b9NsVIv2WqnSWE5/cf9+DmCbEMLo1lb8pvzFC6ZU31YME67X5To+BcdtE330WE+C6JFUAH9yv9s8YfYRMQwBNC9vvfx5A=';
ScanditSDK.configure(key, {
    engineLocation: "https://cdn.jsdelivr.net/npm/scandit-sdk@5.x/build/",
    })
    .then(() => {
        return ScanditSDK.BarcodePicker.create(document.getElementById("scandit-barcode-picker"), {
            playSoundOnScan: false,
            vibrateOnScan: false,
            videoFit: ScanditSDK.BarcodePicker.ObjectFit.COVER,
            scanSettings: new ScanditSDK.ScanSettings({ enabledSymbologies: ["ean8", "ean13", "upca", "upce"] }),
        });
    }).then((barcodePicker) => {
        // barcodePicker is ready here, show a message every time a barcode is scanned
        barcodePicker.on("scan", (scanResult) => {
            var barcode = scanResult.barcodes[0].data.toString(10)
            var barcodeType = scanResult.barcodes[0].symbology
            console.log("[INFO] Scanned: "+barcode+", "+barcodeType)
            if (barcode == currScannedBarcode) {
                console.log("[INFO] Scanned barcode, but already currently scanned")
            } else {
                currScannedBarcode = barcode;
                console.log("[INFO] Scanned barcode and performing lookup")
                mockFunc(barcode, barcodeType);
            }
        });
});



function setScannedItem(name, barcode, barcodeType, quantity) {
    localStorage.setItem("scannedItem", JSON.stringify([name, barcode, barcodeType, quantity]))
}
function getScannedItem() {
    return JSON.parse(localStorage.getItem("scannedItem"));
}
function destroyScannedItem() {
    localStorage.setItem("scannedItem", null);
}
function isScannedItem() {
    if (getScannedItem() != null) {
        return true;
    }
    return false;
}


var currScannedBarcode = null;
var currScannedItem = null;
var currScannedItemQuantity = 1;
var displayedItem = null;

var productScanning = false;
var currentlyDisplayedProductBarcode = null;

var subtotalButton = document.getElementById("subtotalButton");
subtotalButton.innerHTML = "$"+getCartSubtotal();
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
    currScannedBarcode = null;
    console.log("Cancel button pressed")
    productCardHide();
};

confirmButton.onclick = function(){
    currScannedBarcode = null;
    console.log("Confirm button pressed")
    // TODO add product to cart
    productCardHide();
    addItemToCart(currScannedItem.name, currScannedItem.barcode, currScannedItem.barcodeType, currScannedItemQuantity, currScannedItem.price);
    subtotalButton.innerHTML = "$"+getCartSubtotal();
 };

plusButton.onclick = function(){
    console.log("Plus button pressed")
    productQuantityDiv.innerHTML = parseInt(productQuantityDiv.innerHTML) + 1;
    currScannedItemQuantity = parseInt(productQuantityDiv.innerHTML);
}

minusButton.onclick = function(){
    console.log("Minus button pressed")
    if(parseInt(productQuantityDiv.innerHTML) > 1)
    {
        productQuantityDiv.innerHTML = parseInt(productQuantityDiv.innerHTML) - 1;
        currScannedItemQuantity = parseInt(productQuantityDiv.innerHTML);
    }
}


function mockFunc(barcode, barcodeType) {
    var milliseconds = 500;
    console.log("[INFO] Waiting "+milliseconds+" milliseconds")
    setTimeout(function(){ mockProductLookup(barcode, barcodeType); }, milliseconds);
}
function mockProductLookup(barcode, barcodeType) {
    // todo: api lookup of product here
    console.log("[INFO] /lookup of "+barcode+", "+barcodeType)
    var testProduct = '{ "_id" : { "$oid" : "5f69529fda276d39ff5371d5" }, "barcode" : "9780061241895", "barcodeType" : "ean13", "name" : "Water", "price" : 0.99, "description" : "Yes, you have to pay for this basic necessity.", "businessID" : "1", "time" : { "$numberLong" : "1600737951212" } }';
    var respProduct = JSON.parse(testProduct);
    currScannedItem = respProduct
    displayItem(currScannedItem);
}
// expects valid javascript product object
function displayItem(item)
{
    console.log("Displaying product card...")
    var itemQuantity = 1;
    if (existsInCart(currScannedItem.barcode,currScannedItem.barcodeType)) {
        console.log("[INFO] Retrieving previous scan quantity")
        itemQuantity = getCartItemQuantity(currScannedItem.barcode)
    }
    productQuantityDiv.innerHTML = itemQuantity;
    if (currScannedItem != null) {
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
        productCardShow();
        start = undefined
    } else {
        console.log("[ERROR] No current product to use for displayItem()")
    }
}

function productCardShow() {
    scannedProductDiv.style.display = "block";
}

function productCardHide() {
    // no product currently showing
    currentlyDisplayedProductBarcode = null;
    scannedProductDiv.style.display = "none";
}
// END - PRODUCT CARD CODE
