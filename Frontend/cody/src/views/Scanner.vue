<template>
  <!--PRODUCT SCANNER ELEMENT-->
  <div id="Scanner">
    <div class="scanner" id="scandit-barcode-picker"></div>
    <div id="checkoutBtn" class='btn-bot-left'>
        <div class="inner text scannerButtonText">
            Checkout
        </div>
    </div>
    <div class='btn-bot-right'>
        <div class="inner text scannerButtonText" id="subtotalButton">
            {{ total }}
        </div>
    </div>
  </div>
  <!--END - PRODUCT SCANNER ELEMENT-->
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    total: Number
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/*PRODUCT SCANNER STYLING*/
.scanner {
    height: 100vh; /* For 100% screen height */
    width:  100vw; /* For 100% screen width */
    margin: 0;
    position: absolute;
    top: 50%;
    left: 50%;
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
}

.btn-bot-left {
    position: absolute;
    display: table;
    text-align: center;
    border-radius: 20px;
    left: 33%;
    width: 33%;
    height: 7%;
    margin-left: -16.5%;
    bottom: 3%;
    background: #FAFAFA;
}
.btn-bot-right {
    position: absolute;
    display: table;
    text-align: center;
    border-radius: 20px;
    left: 66%;
    width: 33%;
    height: 7%;
    margin-left: -16.5%;
    bottom: 3%;
    background: #E0E0E0;
}

.inner{
  display: table-cell;
  vertical-align: middle;
}
/*END - PRODUCT SCANNER STYLING*/

</style>