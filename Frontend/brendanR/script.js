var currentDisplayItem = null;
var scannedProductDiv = document.getElementById("scannedProductDiv")
var displayName = document.getElementById("nameText")
var displayPrice = document.getElementById("priceText")
var displayQuantity = document.getElementById("quantityText")

var downButton = document.getElementById("downButton")
var plusButton = document.getElementById("plusButton")
var minusButton = document.getElementById("minusButton")
var confirmButton = document.getElementById("confirmButton")

function displayItem(item)
{
    price = item.price
    name = item.name
    item["quantity"] = 1
    displayName.innerHTML = item.name
    console.log(displayName.innerHTML)
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
    displayItem(testItem)
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