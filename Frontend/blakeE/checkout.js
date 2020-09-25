// PRODUCT SCANNER CODE
var quantityText = null;
var checkoutBtn = document.getElementById("checkoutBtn")

checkoutBtn.onclick = function(){
   console.log("Moving to checkout cart view...")
};

function mockProductLookup() {
    var resp = '{ "_id" : { "$oid" : "5f69529fda276d39ff5371d5" }, "barcode" : "9780061241895", "barcodeType" : "ean13", "name" : "Influence, The Psychology of Persuasion", "price" : 10.98, "description" : "National Best Seller. By Robert B. Cialdini, PH.D.", "businessID" : "1", "time" : { "$numberLong" : "1600737951212" } }'
    var returnedProduct = JSON.parse(resp);
    displayItem(returnedProduct);
    productScanning = false;
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
