<template>
  <!--PRODUCT SCANNER ELEMENT-->
  <div class="scanner" id="scandit-barcode-picker"></div>
  <!--END - PRODUCT SCANNER ELEMENT-->
</template>

<script>
export default {
  name: 'Scanner',
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/*PRODUCT SCANNER STYLING*/
.scanner {
    height: 95vh; /* For 100% screen height */
    width:  100vw; /* For 100% screen width */
    margin: 0;
    position: absolute;
    top: 50%;
    left: 50%;
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
}
/*END - PRODUCT SCANNER STYLING*/

</style>