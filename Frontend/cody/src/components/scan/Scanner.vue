<template>
  <div>
    <ScanButtons 
      v-bind:total=getCartSubtotal />
    <v-bottom-sheet
      v-model="show_scanned_product"
      inset
    >
    <v-sheet>
      <v-card>
        <Product 
        v-bind:barcode="scanned_product_barcode"
        v-bind:elevation="0"
        v-on:removed-product="hideScannedProductCard"
        />
        <v-card-actions>
          <v-row align="center" justify="center">
            <v-col>
              <v-btn @click="cancelScannedProduct" icon>
                <v-icon>mdi-close-circle</v-icon>
              </v-btn>
            </v-col>
            <v-col>
              <v-btn @click="hideScannedProductCard" icon>
                <v-icon>mdi-check</v-icon>
              </v-btn>
            </v-col>
          </v-row>
        </v-card-actions>
      </v-card>
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
      scanned_product_barcode: null,
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
      this.scanned_product_barcode = barcode
      var product = this.getCart.find(product => product.barcode == barcode)
      // if product is in cart
      if(product!==undefined)
        this.product_loaded_from_cart = true
      else {
        // TODO: product = result of query to backend for product (need to handle if no item found)
        // TODO: remove fake scanned product when above TODOs are completed
        product = {
          barcode:barcode,
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
      // save the state of the scanned product before allowing user to make changes
      this.initial_product_quantity = product.quantity
      this.show_scanned_product = true
      // TODO: implement function to pause scanner while show_scanned_product = true
    },
    // if user cancels adding the scanned product, we need to undo any changes they have made
    // if the product was loaded from the cart, we need to restore the product's quantity
    // if the product was not originally from the cart, we should remove it from the cart
    hideScannedProductCard() {
      this.show_scanned_product = false
    },
    cancelScannedProduct() {
      this.hideScannedProductCard()
      if(this.product_loaded_from_cart === true) {
        this.setProductQuantity({barcode:this.scanned_product_barcode,
                                 amount:this.initial_product_quantity})
      }
      else
      {
        this.removeProduct({barcode:this.scanned_product_barcode}) 
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->