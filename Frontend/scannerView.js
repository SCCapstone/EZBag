console.log("Script running!")
object = document.getElementById("myButton")
object.onclick = function(){
    console.log("Hello world!")
    callPost()
};

var callPost = function() {
    queryObj = {"productCode":"810180026178"}
    var aUrl = "http://localhost:8080/EZBagWebapp/webapi/products";
    var xhr = $.ajax({
        type: "POST",
        data: JSON.stringify(queryObj),
        location: "",
        url: aUrl, 
        success: function(output, status) { 
            alert(output);
            console.log(output)
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