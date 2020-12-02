<template>
  <div>
    <div class="scanner" id="scandit-barcode-picker"></div>
    <ScanButtons 
      v-bind:total=getCartSubtotal />
    <v-bottom-sheet
      v-model="show_scanned_product"
      inset
    >
    <v-sheet class="text-center">
      <v-btn
        @click=cancelScannedProduct
      >cancel</v-btn>
        <Product 
        v-bind:barcode=scanned_product.barcode
        v-bind:name=scanned_product.name
        v-bind:price=scanned_product.price
        v-bind:description=scanned_product.description
        v-bind:businessID=scanned_product.businessID
        v-bind:quantity=scanned_product.quantity
        v-on:removed-product="hideAndResetScannedProductCard"
        />
      </v-sheet>
    </v-bottom-sheet>
  </div>
</template>

<script>
import { mapGetters, mapActions} from 'vuex';
import ScanButtons from '@/components/scan/ScanButtons'
import Product from '@/components/checkout/Product'

export default {
  name: 'Scanner',
  components: {
    ScanButtons,
    Product
  },
  computed: mapGetters(['getCartSubtotal', 'getCart']),
  data() {
    return {
      show_scanned_product: false, // for displaying scanned product card
      scanned_product: {
        barcode:null,
        name:null,
        price:null,
        description:null,
        businessID:null,
        quantity:null
      },
      initial_product_quantity: null,
      scanned_product_loaded_from_cart: false,
    };
  },
  methods:{
    ...mapActions([ "removeProduct",
                    "setProductQuantity",
                    "addProduct"
                  ]),
    onScan(barcode) {
      // get product from cart, if it is in cart
      var product = this.getCart.find(product => product.barcode == barcode)
      // if product is in cart
      if(product!==undefined) {
        this.product_loaded_from_cart = true
      }
      else{
        // TODO: product = result of query to backend for product (need to handle if no item found)
        // TODO: remove fake scanned product when above TODOs are completed
        product = {
          barcode:"scanned",
          name:"Totally loaded from the backend",
          price: 5.99,
          tax: 0.36,
          description:"We definitely didn't cheat",
          businessID:"1",
          quantity: 1,
        }
        this.addProduct({barcode:product.barcode,
                         name:product.name,
                         price:product.price,
                         description:product.description,
                         businessID:product.businessID})
        this.product_loaded_from_cart = false
      }
      // assert: product is in cart (either was already in cart, or was loaded from backend)
      this.scanned_product = product
      // save the state of the scanned product before allowing user to make changes
      this.initial_product_quantity = product.quantity
      this.show_scanned_product = true
      // TODO: implement function to pause scanner while show_scanned_product = true
    },
    // if user cancels adding the scanned product, we need to undo any changes they have made
    // if the product was loaded from the cart, we need to restore the product's quantity
    // if the product was not originally from the cart, we should remove it from the cart
    cancelScannedProduct() {
      if(this.product_loaded_from_cart === true) {
        this.setProductQuantity({barcode:this.scanned_product.barcode,
                                 quantity:this.initial_product_quantity})
      }
      else
        this.removeProduct(this.scanned_product.barcode)
      this.hideAndResetScannedProductCard()
    },
    hideAndResetScannedProductCard() {
      this.show_scanned_product = false
      this.initial_product_quantity = null
      this.scanned_product_loaded_from_cart = false
      this.scanned_product= {
        barcode:null,
        name:null,
        price:null,
        description:null,
        businessID:null,
        quantity:null
      }
    }
  }
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
</style>