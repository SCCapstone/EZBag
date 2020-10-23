
var clientSecret = ""; // place holder until get request gets new client secret to perform transaction

addItemToCart("Cherry Coke", "1111111111111", "ean13", 2, 1.99)
addItemToCart("Coca Cola", "2222222222222", "ean13", 1, 1.18)
addItemToCart("Mountain Dew", "3333333333333", "ean13", 3, 1.66)
addItemToCart("Doritos", "4444444444444", "ean13", 1, 2.99)
addItemToCart("Cherry Coke", "1111111111111", "ean13", 2, 1.99)
addItemToCart("Coca Cola", "2222222222222", "ean13", 1, 1.18)
addItemToCart("Mountain Dew", "3333333333333", "ean13", 3, 1.66)
addItemToCart("Doritos", "4444444444444", "ean13", 1, 2.99)
addItemToCart("Cherry Coke", "1111111111111", "ean13", 2, 1.99)
addItemToCart("Coca Cola", "2222222222222", "ean13", 1, 1.18)
addItemToCart("Mountain Dew", "3333333333333", "ean13", 3, 1.66)
addItemToCart("Doritos", "4444444444444", "ean13", 1, 2.99)

var cartTotalField = document.getElementById("cartTotal");
cartTotalField.innerHTML = "<b>$"+String(getCartSubtotal())+"</b>"

// TODO: generate list items based on cart contents
var currCart = getCart();
var names = currCart.names;
var prices = currCart.prices;
var quantities = currCart.quantities;
for (var i = 0; i<names.length; i++) {
    var node = document.createElement("LI");
    var subDiv = document.createElement("div");
    subDiv.className = "listItemContainer";
    node.appendChild(subDiv);
    var liInput = document.createElement("input");
    liInput.className = "quantityInput";
    // type="number" id="quantity" name="quantity" min="1" max="100" value="1"
    liInput.type = "number";
    liInput.id = "quantity";
    liInput.name = "quantity";
    liInput.min = "1";
    liInput.max = "100";
    liInput.value = String(quantities[i]);
    subDiv.appendChild(liInput);
    var liPrice = document.createElement("div");
    liPrice.className = "priceText";
    liPrice.innerHTML = "$"+prices[i];
    subDiv.appendChild(liPrice);
    var liName = document.createElement("div");
    liName.className = "listItemText";
    liName.innerHTML = names[i];
    subDiv.appendChild(liName);
    document.getElementById("list").appendChild(node);
}

$("input[type='number']").change( function() {
    var nums = document.getElementById("list");
    var listItem = nums.getElementsByTagName("li");
    var newNums = [];
    for (var i=0; i < listItem.length; i++) {
        newNums.push( parseInt( (listItem[i].children)[0].children[0].value ));
    }
    var currCart = getCart();
    currCart.quantities = newNums;
    setCart(currCart);
    var cartTotalField = document.getElementById("cartTotal");
    cartTotalField.innerHTML = "<b>$"+String(getCartSubtotal())+"</b>"
});

var getClientSecret = function(payload, route) {
    $.get("/webapi/pay", function(data, status){

var clientSecret = "pi_1HXt5EEodw3j5gAJot2TSWRa_secret_WCBwbA7EFZyKpYgvPKkoACh1s";

// addItemToCart("Cherry Coke", "1111111111111", "ean13", 2, 1.99)
// addItemToCart("Coca Cola", "2222222222222", "ean13", 1, 1.18)
// addItemToCart("Mountain Dew", "3333333333333", "ean13", 3, 1.66)
// addItemToCart("Doritos", "4444444444444", "ean13", 1, 2.99)
// addItemToCart("Cherry Coke", "1111111111111", "ean13", 2, 1.99)
// addItemToCart("Coca Cola", "2222222222222", "ean13", 1, 1.18)
// addItemToCart("Mountain Dew", "3333333333333", "ean13", 3, 1.66)
// addItemToCart("Doritos", "4444444444444", "ean13", 1, 2.99)
// addItemToCart("Cherry Coke", "1111111111111", "ean13", 2, 1.99)
// addItemToCart("Coca Cola", "2222222222222", "ean13", 1, 1.18)
// addItemToCart("Mountain Dew", "3333333333333", "ean13", 3, 1.66)
// addItemToCart("Doritos", "4444444444444", "ean13", 1, 2.99)

var cartTotalField = document.getElementById("cartTotal");
cartTotalField.innerHTML = "<b>$"+String(getCartSubtotal())+"</b>"

// TODO: generate list items based on cart contents
var currCart = getCart();
var names = currCart.names;
var prices = currCart.prices;
var quantities = currCart.quantities;
for (var i = 0; i<names.length; i++) {
    var node = document.createElement("LI");
    var subDiv = document.createElement("div");
    subDiv.className = "listItemContainer";
    node.appendChild(subDiv);
    var liInput = document.createElement("input");
    liInput.className = "quantityInput";
    // type="number" id="quantity" name="quantity" min="1" max="100" value="1"
    liInput.type = "number";
    liInput.id = "quantity";
    liInput.name = "quantity";
    liInput.min = "1";
    liInput.max = "100";
    liInput.value = String(quantities[i]);
    subDiv.appendChild(liInput);
    var liPrice = document.createElement("div");
    liPrice.className = "priceText";
    liPrice.innerHTML = "$"+prices[i];
    subDiv.appendChild(liPrice);
    var liName = document.createElement("div");
    liName.className = "listItemText";
    liName.innerHTML = names[i];
    subDiv.appendChild(liName);
    document.getElementById("list").appendChild(node);
}

$("input[type='number']").change( function() {
    var nums = document.getElementById("list");
    var listItem = nums.getElementsByTagName("li");
    var newNums = [];
    for (var i=0; i < listItem.length; i++) {
        newNums.push( parseInt( (listItem[i].children)[0].children[0].value ));
    }
    var currCart = getCart();
    currCart.quantities = newNums;
    setCart(currCart);
    var cartTotalField = document.getElementById("cartTotal");
    cartTotalField.innerHTML = "<b>$"+String(getCartSubtotal())+"</b>"
});

var getClientSecret = function(payload, route) {
    $.get("/webapi/pay", function(data, status){
        var getClientSecret = data;
        var data = JSON.parse(data);
        // ex : "pi_1HXt5EEodw3j5gAJot2TSWRa_secret_WCBwbA7EFZyKpYgvPKkoACh1s"; 
        clientSecret = data.client_secret;
    });
};


// TODO: get client secret from API

// create stripe object
var stripe = Stripe('pk_test_51HWQbDEodw3j5gAJBjvOQ4GxneRlIocvxEdN97ThEyFs9fqRpkf6SXik8fZZOcMKLvXYoa4HQccNxmnVvqxKc4dL00gDcPUMiA', {
    apiVersion: "2020-08-27",
});

// create payment request instance
var paymentRequest = stripe.paymentRequest({
    country: 'US',
    currency: 'usd',
    total: {
    label: 'Demo total',
    amount: 1313,
    },
    requestPayerName: true,
    requestPayerEmail: true,
});

// create and mount paymentRequestButton
var elements = stripe.elements();
var prButton = elements.create('paymentRequestButton', {
    paymentRequest: paymentRequest,
});

// Check the availability of the Payment Request API first.
paymentRequest.canMakePayment().then(function(result) {
    if (result) {
    prButton.mount('#payment-request-button');
    } else {
    document.getElementById('payment-request-button').style.display = 'none';
    // alert("Payment Request API unavailable!");
    }
});

paymentRequest.on('paymentmethod', function(ev) {
    // Confirm the PaymentIntent without handling potential next actions (yet).
    stripe.confirmCardPayment(
    clientSecret,
    {payment_method: ev.paymentMethod.id},
    {handleActions: false}
    ).then(function(confirmResult) {
    if (confirmResult.error) {
        // Report to the browser that the payment failed, prompting it to
        // re-show the payment interface, or show an error message and close
        // the payment interface.
        ev.complete('fail');
        window.location.pathname = '/success.html'
    } else {
        // Report to the browser that the confirmation was successful, prompting
        // it to close the browser payment method collection interface.
        ev.complete('success');
        // Check if the PaymentIntent requires any actions and if so let Stripe.js
        // handle the flow. If using an API version older than "2019-02-11" instead
        // instead check for: `paymentIntent.status === "requires_source_action"`.
        if (confirmResult.paymentIntent.status === "requires_action") {
        // Let Stripe.js handle the rest of the payment flow.
        stripe.confirmCardPayment(clientSecret).then(function(result) {
            if (result.error) {
            // The payment failed -- ask your customer for a new payment method.
            } else {
            // The payment has succeeded.
            window.location.pathname = '/success.html'
            }
        });
        } else {
        // The payment has succeeded.
        window.location.pathname = '/success.html'
        }
    }
    });
});    });
};


// TODO: get client secret from API

// create stripe object
var stripe = Stripe('pk_test_51HWQbDEodw3j5gAJBjvOQ4GxneRlIocvxEdN97ThEyFs9fqRpkf6SXik8fZZOcMKLvXYoa4HQccNxmnVvqxKc4dL00gDcPUMiA', {
    apiVersion: "2020-08-27",
});

// create payment request instance
var paymentRequest = stripe.paymentRequest({
    country: 'US',
    currency: 'usd',
    total: {
    label: 'Demo total',
    amount: 1313,
    },
    requestPayerName: true,
    requestPayerEmail: true,
});

// create and mount paymentRequestButton
var elements = stripe.elements();
var prButton = elements.create('paymentRequestButton', {
    paymentRequest: paymentRequest,
});

// Check the availability of the Payment Request API first.
paymentRequest.canMakePayment().then(function(result) {
    if (result) {
    prButton.mount('#payment-request-button');
    } else {
    document.getElementById('payment-request-button').style.display = 'none';
    // alert("Payment Request API unavailable!");
    }
});

paymentRequest.on('paymentmethod', function(ev) {
    // Confirm the PaymentIntent without handling potential next actions (yet).
    stripe.confirmCardPayment(
    clientSecret,
    {payment_method: ev.paymentMethod.id},
    {handleActions: false}
    ).then(function(confirmResult) {
    if (confirmResult.error) {
        // Report to the browser that the payment failed, prompting it to
        // re-show the payment interface, or show an error message and close
        // the payment interface.
        ev.complete('fail');
        window.location.pathname = '/success.html'
    } else {
        // Report to the browser that the confirmation was successful, prompting
        // it to close the browser payment method collection interface.
        ev.complete('success');
        // Check if the PaymentIntent requires any actions and if so let Stripe.js
        // handle the flow. If using an API version older than "2019-02-11" instead
        // instead check for: `paymentIntent.status === "requires_source_action"`.
        if (confirmResult.paymentIntent.status === "requires_action") {
        // Let Stripe.js handle the rest of the payment flow.
        stripe.confirmCardPayment(clientSecret).then(function(result) {
            if (result.error) {
            // The payment failed -- ask your customer for a new payment method.
            } else {
            // The payment has succeeded.
            window.location.pathname = '/success.html'
            }
        });
        } else {
        // The payment has succeeded.
        window.location.pathname = '/success.html'
        }
    }
    });
});