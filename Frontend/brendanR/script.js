var currentDisplayItem = null;
var scannedProductDiv = document.getElementById("scannedProductDiv")
var displayName = document.getElementById("nameText")
var displayPrice = document.getElementById("priceText")
var displayQuantity = document.getElementById("quantityText")

var downButton = document.getElementById("downButton")
var plusButton = document.getElementById("plusButton")
var minusButton = document.getElementById("minusButton")
var confirmButton = document.getElementById("confirmButton")
var maxNameLength = 15

function displayItem(item)
{
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
    currentDisplayItem = item
    window.requestAnimationFrame(slideInAnimation);
    start = undefined
}

var testItem = {
    "name":"Peanut Butter",
    "price":"4.20",
}

$('button').bind('touchstart', function(){
    $(this).addClass('buttonActive');
}).bind('touchend', function(){
    $(this).removeClass('buttonActive');
});

plusButton.ontouchend = function()
{
    currentDisplayItem.quantity = currentDisplayItem.quantity + 1
    displayQuantity.innerHTML = currentDisplayItem.quantity
}

minusButton.ontouchend = function()
{
    if(currentDisplayItem.quantity >= 1)
    {
        currentDisplayItem.quantity = currentDisplayItem.quantity - 1
    }
    displayQuantity.innerHTML = currentDisplayItem.quantity.toString()
}

downButton.ontouchend = function()
{
    window.requestAnimationFrame(slideOutAnimation);
    start = undefined
}

confirmButton.ontouchend = function()
{
    window.requestAnimationFrame(slideOutAnimation);
    start = undefined
}

window.addEventListener('load', function () {
    var resp = '{ "_id" : { "$oid" : "5f69529fda276d39ff5371d5" }, "barcode" : "9780061241895", "barcodeType" : "ean13", "name" : "Influence, The Psychology of Persuasion", "price" : 10.98, "description" : "National Best Seller. By Robert B. Cialdini, PH.D.", "businessID" : "1", "time" : { "$numberLong" : "1600737951212" } }'
    var returnedProduct = JSON.parse(resp);
    displayItem(returnedProduct)
})

let start;

function slideOutAnimation(timestamp) {

    if (start === undefined)
        start = timestamp;
    const elapsed = timestamp - start;

    scannedProductDiv.style.bottom = Math.max(-elapsed*0.4, -70) + "%"

    if (elapsed < 175) { // Stop the animation after 2 seconds
        window.requestAnimationFrame(slideOutAnimation);
    }
}

function slideInAnimation(timestamp) {
    if (start === undefined)
        start = timestamp;
    const elapsed = timestamp - start;

    scannedProductDiv.style.bottom = Math.min((elapsed-175)*0.4, 0) + "%"

    if (elapsed < 175) { // Stop the animation after 2 seconds
        window.requestAnimationFrame(slideInAnimation);
    }
}