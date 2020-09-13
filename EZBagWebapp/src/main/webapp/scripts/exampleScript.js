const key = "AQZOrRteBEkNL00Y2BzlDawGLF+INUj7V0OVEW4Lmj5oYTPF+kjyUjA3teTSf6JkdBp2jJxHosY9bJjU5kAHEgJ2TgfxJmP/3XKgn7ZIoW+laxrLzHsC4E1DGE9uePRx/Webii0FzQgYJij0agLqr+6v54OnkD6bUPbyAtVXf86RJHNkMlMkZDXyrOkAkbFz+6s7xAKEaXdsFOxxtB2I94VvbEtg/Y5DjumsqwuGs+uQEYgo40I28fAY04uegvVzhPJxy2Uajgod5qcda4Ou3zLkisEBZd8kFmGk2XWWfFR5eViTjiAxncRNmvrYABiF6jA78BVH70/H/Ca3JX3s7E/Y8VdyE+QUW4t5TVmPQA9Dl6WGYQmxWEB0TrRJ+upZBYQhkHBNDEnazyZpIRc4MjnSLmjh8E9fMOelewwDVh39mdo09asD/l7SJH8w1OQmPZ6df6QjvI8nZ8O2xdQNKZW6UrKAYF4lhTOohddaggikYQ5pxUp8WH+87fDiDo7VcRcBXPiGwCoRGvmc7GAibybhz0mJ50mX5h+5rqLNONo5iHCD8HGTv9FOIgaScV8/TkeX1rB49Kcbkp/8D5dQV0QiiqxCF1dNGKMmd75RKFjBnOyGvOo0QN3pALJ5L1Ug68vfVsLqa7OWPypnvNhRYrmWvdDPKoMoJqhpN5UJPX2M/Q1bgZlwxZeSGHlDFphJNXOoMDhH6uNFjbgwzLNfe0p8bXBmYUrsz0b9NsVIv2WqnSWE5/cf9+DmCbEMLo1lb8pvzFC6ZU31YME67X5To+BcdtE330WE+C6JFUAH9yv9s8YfYRMQwBNC9vvfx5A=";
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
            var barcode = scanResult.barcodes[0].data
            callPost(barcode.toString(10))
        });

});

console.log("Script running!")
object = document.getElementById("myButton")
object.onclick = function(){
    console.log("Hello world!");
    callPost();
};

var callPost = function(barcode) {
    queryObj = {"productCode":barcode}
    console.log(queryObj)
    var aUrl = "http://localhost:8080/EZBagWebapp/webapi/products";
    var xhr = $.ajax({
        type: "POST",
        data: JSON.stringify(queryObj),
        location: "",
        url: aUrl, 
        success: function(output, status) {
            alert(output);
            console.log(output);
            $('.sysMsg').html(output);
        },
        error: function(output) {
          $('.sysMsg').html(output);
        }
    });
  };

var callPostExample = function() {
    searchString = "hello from client"
    var aUrl = "http://localhost:8080/EZBagWebapp/webapi/test";
    var xhr = $.ajax({
        type: "POST",
        data: searchString,
        location: "",
        url: aUrl, 
        success: function(output, status) { 
            console.log(output)
            $('.sysMsg').html(output);
        },
        error: function(output) {
          $('.sysMsg').html(output);
        }
    });
  };

