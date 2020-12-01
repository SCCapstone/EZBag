<template>
  <div>
    <div class="scanner" id="scandit-barcode-picker"></div>
    <ScanButtons 
      v-bind:total=getCartTotal />
    <div id="scannedProductDiv">
      <ScannedProduct 
        v-bind:barcode=product.barcode
        v-bind:name=product.name
        v-bind:price=product.price
        v-bind:description=product.description
        v-bind:businessID=product.businessID
        v-bind:quantity=product.quantity
        />
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import ScanButtons from '@/components/scan/ScanButtons'
import ScannedProduct from '@/components/scan/ScannedProduct'

export default {
  name: 'Scanner',
  components: {
    ScanButtons,
    ScannedProduct
  },
  computed: mapGetters(['getCart', 'getCartTotal', 'getProductFromCart']),
  data: function () {
    return {
      product: {
        barcode: "12345678",
        name: "Hello World",
        price: 10.99,
        description: "This is a mock product",
        businessID: "1",
        quantity: 2,
      },
    }
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
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

  #scannedProductDiv{
        position: absolute;
        background-color: #e0e0e0;
        bottom: 0%;
        width: 100%;
        /* z-index: 10; */
    }

</style>