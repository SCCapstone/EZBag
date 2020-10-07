// setup local storage object
localStorage = window.localStorage

// todo: will be received via link
localStorage.setItem("businessID", "1");


// check for or create new cart object
if (cartExists()) {
    console.log("[INFO] Existing cart was found!")
} else {
    console.log("[INFO] No existing cart, creating new cart!")
    createCart();
    console.log("[SUCCESS] Created new customer cart!")
}
function generateSessionID() { 
    idCharset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789"
    length = 25;
    var uniqueID = ''; 
    for (var i=0; i<length; i++) { 
        uniqueID += idCharset[Math.floor(Math.random() * idCharset.length)]; 
    } 
    return uniqueID; 
} 
function cartExists() {
    if (getCart() != null) {
        return true;
    }
    return false;
}
function createCart() {
    var newCart = {
        names: [],
        barcodes: [], 
        barcodeTypes: [],
        quantities: [],
        prices: [],
        session: generateSessionID(),
        businessID: localStorage.getItem("businessID")
    }
    setCart(newCart);
}
function destroyCart() {
    setCart(null);
    console.log("[SUCCESS] Destroyed cart")
}
// cart helper methods
function setCart(newCartArr) {
    localStorage.setItem("cart", JSON.stringify(newCartArr));
}
function getCart(){
    return JSON.parse(localStorage.getItem("cart"));
}
function getSession() {
    if (cartExists()) {
        return getCart().session;
    } else {
        console.log("[ERROR] Cart does not exist");
        return null;
    }
}
function setSession(session) {
    var newCart = getCart();
    newCart.session = session;
    setCart(newCart);
    console.log("[SUCCESS] Set new session ID")
}
function getBusinessID() {
    if (cartExists()) {
        return getCart().businessID;
    } else {
        console.log("[ERROR] Cart does not exist");
        return null;
    }
}
function setBusinessID(businessID) {
    var newCart = getCart();
    newCart.businessID = businessID;
    setCart(newCart);
    console.log("[SUCCESS] Set new business ID")
}
function existsInCart(barcode, barcodeType) {
    if (getCart().barcodes.indexOf(barcode) > -1) {
        return true;
    }
    return false;
}
function getCartItemQuantity(barcode) {
    var currCart = getCart();
    var itemIdx = currCart.barcodes.indexOf(barcode);
    if (itemIdx > -1) {
        return currCart.quantities[itemIdx]
    } else {
        console.log("[ERROR] Cannot get quantity of non-existant cart item")
    }
}
function addItemToCart(name, barcode, barcodeType, quantity, price) {
    console.log("[INFO] Cart received new item: "+name+", "+barcode+", "+barcodeType+", "+quantity+", "+price)
    if (existsInCart(barcode, barcodeType)) {
        console.log("[INFO] Product exists in cart")
        removeItemFromCart(barcode);
    }
    var newCart = getCart();
    newCart.names.push(name);
    newCart.barcodes.push(barcode);
    newCart.barcodeTypes.push(barcodeType);
    newCart.quantities.push(quantity);
    newCart.prices.push(price)
    setCart(newCart);
    console.log("[SUCCESS] Added new cart item")
}
function removeItemFromCart(barcode) {
    var newCart = getCart();
    var itemIdx = newCart.barcodes.indexOf(barcode);
    if (itemIdx > -1) {
        newCart.names.splice(itemIdx, 1);
        newCart.barcodes.splice(itemIdx, 1);
        newCart.barcodeTypes.splice(itemIdx, 1);
        newCart.quantities.splice(itemIdx, 1);
        newCart.prices.splice(itemIdx, 1);
        setCart(newCart);
        console.log("[SUCCESS] Item removed from cart")
    } else {
        console.log("[ERROR] Item not found in cart")
    }
}
function getCartSubtotal() {
    if (getCart() != null) {
        var prices = getCart().prices;
        var quantities = getCart().quantities;
        var subtotal = 0.0
        for (var i=0; i<prices.length; i++) {
            subtotal += quantities[i]*prices[i];
        }
        return subtotal.toFixed(2);
    } else {
        console.log("[ERROR] Cannot calculate subtotal because no cart object ")
    }
    
}
// todo feature: if already in your cart and you scanned it, display the current quantity added before