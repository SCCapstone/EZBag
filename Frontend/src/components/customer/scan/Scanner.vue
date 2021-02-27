<template>
  <div>

    <v-scandit class="v-scandit"
      license-key="ARv/1yo4Cha6Htq90Q/asSQ0VGsHRkfIKHyFSktTSM5faaemV3L89GZ4d67GXGERDAwSfJ5KEriIQSJUmBd64fN8R0x6Cph9xnbkKSpspIecckli/WLQP9N9dAmqfc7Up2xSEahD4AxSftZdigk6I9EZ8fesCPxqGUTa/XrCiO7D4ZS5vdwybGJJJo3SkJxiPQ6dya/m5z7DzfINzfFHCmj6fgnQR/TFINTsCklDnr8/DEpa+/hYLE2xw8SYA4205UViJRO0lCKpJfw5VeoGtglpJmQ9H65lgJOGCTkPSX1wX9cX7moF0A3cMto2ET+sjR+yaa98j5zCHzf8UZZUa2fCBgt1ud4pXm8XXqYNihP6EU9ryhFuw6dut1BIDoTkbD8jnZCWNl1ixYmpWRn/5kJXJwsvKfTGJ3UopiIbPWk1GH95g/Z1pIrtUjnEPfBNIMuYtTQWR7faTBUqLomgvtT9QCJ+FrSzzqdzUj1t/Sp12nm4yLOrRWJPagK0taAkSgpBf8Uzc6jhmwmLvgC2cWx7RuKIvOd8eSif4ZegBMbrasWfI5GR1it2bwnv0rVy3bD06eZdo3zLkNLfMz7jcFOMsMXjXb9VsgsFzMaq4HowXvvhJurh1+dwevpj80+4kS59YnHZF8GJYcdhcTYeF+tZ8xTE3ExEI4COubvuiRZT8mW8ZI4zaVWzl+ERzdIgYS3Js683+ednxHmakeLHfZtrUuRlTvNpZF5kPAQE9DROUjecaH/LPtGkePuuFUn4ZNfaG4NO60b6+QPPtzKp7mbZprf9Xp1X8IPQOsDjzDQcygecyIkPon0Nj8RLQQBHYLN/mXT5uJDrcA==" 
      :configuration-options="{ engineLocation: 'https://cdn.jsdelivr.net/npm/scandit-sdk@5.x/build/' }"
      :scan-settings="{ enabledSymbologies: ['ean8', 'ean13', 'upca', 'upce']}"
      v-on:barcodePicker="(barcodePicker) => initPicker(barcodePicker)"
      v-on:scan="(barcode) => { onScan(barcode.barcodes[0].data) }" />
    <ScanSearchBar v-on:showproduct="onScan"/>
    <ScanButtons v-bind:total=getSubtotal />

    
    <!--
      Pop up
    !-->
    <div v-show="show_popup==true">
      <v-row justify="center">
          <v-dialog
            v-model="show_popup"
            persistent
            max-width="290"
          >
            <v-card>
              <v-card-title class="headline">
                Item not found!
              </v-card-title>
              <v-card-text>The item you have scanned could not be found in our database!</v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  justify="center"
                  color="green darken-1"
                  text
                  @click="show_popup = false"
                >
                  OK
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
      </v-row>
    </div>
    <!--
      Scan buttons
    !-->
    <v-bottom-sheet
      v-model="show_scanned_product"
      inset
      persistent
    >
      <v-sheet>
        <v-card>
          <Product 
          v-bind:barcode="scanned_product_barcode"
          v-bind:elevation="0"
          v-on:removed-product="hideScannedProductCard"
          />
          <v-card-actions class="justify-center">
            <v-btn @click="cancelScannedProduct">Cancel
              <v-icon right>mdi-close</v-icon>
            </v-btn>
            <v-btn @click="hideScannedProductCard">Add to
              <v-icon right>mdi-cart</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-sheet>
    </v-bottom-sheet>
  </div>
</template>

<script>
import { mapGetters, mapActions, mapMutations} from 'vuex';
import ScanButtons from '@/components/customer/scan/ScanButtons'
import ScanSearchBar from '@/components/customer/scan/ScanSearchBar'
import Product from '@/components/customer/checkout/Product'


export default {
  name: 'Scanner',
  components: {
    ScanButtons,
    ScanSearchBar,
    Product
  },
  data() {
    return {
      show_scanned_product: false, // for displaying scanned product card
      product_loaded_from_cart: false,
      show_popup: false,
      initial_product_quantity: 0,
      scanned_product_barcode: 0,
      barcodePicker: null,
    };
  },
  computed: mapGetters(['getSubtotal', 'getBusinessID']),
  methods:{
    ...mapActions(["addProductToCart"]),
    ...mapMutations(["removeProductFromCart",
                      "setProductQuantity"
                    ]),
    // set options for barcode picker and save picker                     
    initPicker(barcodePicker) {
      barcodePicker.setMirrorImageEnabled(false);
      barcodePicker.setVideoFit('cover');
      this.barcodePicker = barcodePicker
    },
    onScan(barcode) {
      this.barcodePicker.pauseScanning()
      this.$dbg_console_log("scanning paused", this.barcodePicker)
      this.scanned_product_barcode = barcode
      // attempt to add product to cart
      this.addProductToCart({barcode:barcode, businessID:this.getBusinessID})
        .then((result) => { // no backend errors thrown
          this.$dbg_console_log("scanning result")
          this.$dbg_console_log(result)
          if(result.productIsInCart == true){
            if(result.productWasAlreadyInCart){
              // save the product quantity before allowing user to make changes
              this.product_loaded_from_cart = true
              this.initial_product_quantity = result.initialProductQuantity
            }
            this.show_scanned_product = true
          }
          else {
            // TODO: gracefully handle "product not recognized"
            this.show_popup = true
            this.resetBarcodeScanner()
          }
        }).catch(error => {
          this.$dbg_console_log(error)
          this.resetBarcodeScanner()
        })
    },
    // if user cancels adding the scanned product, we need to undo any changes they have made
    // if the product was loaded from the cart, we need to restore the product's quantity
    // if the product was not originally from the cart, we should remove it from the cart
    cancelScannedProduct() {
      if (this.product_loaded_from_cart == true) 
        this.setProductQuantity({
          barcode:this.scanned_product_barcode,
          typeOrAmount: this.initial_product_quantity,
        })
      else
        this.removeProductFromCart(this.scanned_product_barcode) 
      this.hideScannedProductCard()
    },
    hideScannedProductCard() {
      this.show_scanned_product = false;
      this.scanned_product_barcode = 0;
      this.resetBarcodeScanner()
    },
    resetBarcodeScanner() {
      // https://docs.scandit.com/stable/web/classes/barcodepicker.html#clearsession
      this.barcodePicker.resumeScanning();
      this.barcodePicker.clearSession();
      this.$dbg_console_log('resume scanning', this.barcodePicker)
    }
  },
}
</script>

<style scoped>
  .v-scandit {
    position: absolute;
    top: 0%;
    margin: auto;
    height: 100%;
    width: 100%;
  }
</style>