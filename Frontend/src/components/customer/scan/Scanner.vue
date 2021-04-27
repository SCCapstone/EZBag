<template>
  <div id='customer-scanner'>

    <v-scandit
      class="v-scandit"
      license-key="AZ8/Ug5hLjqmMAbzNgY90r9Cv7cjK9PQGVY+fCFQxRXOZLZ0AXBNwblHWITRd1iYdDQmhn1T/lBlYLkcNxURdVluSkcCPPA8qnHIkvJ/vHGcI8kIc1peT7hhv+wUYuDZAFSVGKNAW3YNeLmJLRgtdcgl2BEvIa/D5gQAFmYHWnTeObel0pyvJMwYV3YDv8DL35lmqB3ieAu4Db+OMGJD777/pGzvL7pEX0XucfhLiwuyvH2Gp7s0qQ3p/tEf6YFaIHhj+1MjAsKs+3UvWUqg9oUx0Amh+MLWwxqPSve7SjggQPMOKvdgvU8helgUrCk0ASff/p3lfzeJaVnel3TfJEf2FIzwn69JjsG//TmOgTwtYjVlsXzy5b7UoexWkwZbLanFR42yw2zgTKFiyKEXetFAd+FxMsAOAV84sW9gC05OmZI2NbEt0Y/XOiDcpDIHlvBRUJdliCkXlM2rDCInmU/FL8RZichbH7KrG8RN6Uy462MYVp5wx+vYcwQIwbClHfOFJFBuC47f9idh0xSYS/kPq9mkysjetBZWMGKxZxUiEmxRwQbukwUUz8XfSlgodYUvuyaoqKN46jaIeJlKLil9EFftjnzJBpjKHUFXLfvHqJ90NvI5YTsbOgwBL7sqmkqS6t7Ww+xankZsYkuAwjN/TlA0Tb+3dM8QOhRq1n35jLp01aBR9j6p4bQShY0mwtMAWetzy3uMKOJlGRw6l9ozzRQ62anydApDnQmd4WzhigIJJCKtCP6e1fRN1ZEDDHOkldgoN0nmxJ6R7T5gbm7TXopiYyVSSXEJUfoJ988jwNGTqnjJt144en6O7eBxeK5DEsEy//rPnA==" 
      :configuration-options="{ engineLocation: 'https://cdn.jsdelivr.net/npm/scandit-sdk@5.x/build/' }"
      :scan-settings="{ enabledSymbologies: ['ean8', 'ean13', 'upca', 'upce']}"
      v-on:barcodePicker="(barcodePicker) => initPicker(barcodePicker)"
      v-on:scan="(barcode) => { findAndLoadProduct(barcode.barcodes[0].data) }" />
    <ScanSearchBar v-on:showproduct="findAndLoadProduct" v-on:isSearching="toggleScanner($event)"/>
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
            <v-btn id="cancel-product" @click="cancelScannedProduct">Cancel
              <v-icon right>mdi-close</v-icon>
            </v-btn>
            <v-btn id="add-product" @click="hideScannedProductCard">Add to
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
  computed: mapGetters(['getSubtotal']),
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
    // find product by barcode and open product card if found
    findAndLoadProduct(barcode) {
      // if camera permissions are off, then barcode picker is null
      if(this.barcodePicker != null) {
        this.barcodePicker.pauseScanning()
        this.$dbg_console_log("Scanning paused for load product", this.barcodePicker)
      }
      this.scanned_product_barcode = barcode
      // attempt to add product to cart
      this.addProductToCart({barcode:barcode, businessID:this.$route.params.id})
        .then((result) => { // no backend errors thrown
          this.$dbg_console_log("backend result", result)
          if(result.productIsInCart == true){
            if(result.productWasAlreadyInCart){
              // save the product quantity before allowing user to make changes
              this.product_loaded_from_cart = true
              this.initial_product_quantity = result.initialProductQuantity
            }
            this.show_scanned_product = true
          }
          else {
            this.show_popup = true
            // if camera permissions are off, then barcode picker is null
            if(this.barcodePicker != null) 
              this.resetBarcodeScanner()
          }
        }).catch(error => {
          this.$dbg_console_log(error)
          // if camera permissions are off, then barcode picker is null
          if(this.barcodePicker != null) 
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
      // if camera permissions are off, then barcode picker is null
      if(this.barcodePicker != null)
        this.resetBarcodeScanner()
    },
    resetBarcodeScanner() {
      // https://docs.scandit.com/stable/web/classes/barcodepicker.html#clearsession
      this.barcodePicker.resumeScanning();
      this.barcodePicker.clearSession();
      this.$dbg_console_log('Resume scanning', this.barcodePicker)
    },
    toggleScanner(bool) {
      if (bool) {
        this.$dbg_console_log("Pause scanning for search")
        this.barcodePicker.pauseScanning();
      } else {
        if (!this.show_scanned_product) {
          this.$dbg_console_log("Product card not showing, resuming scanning for search")
          this.barcodePicker.resumeScanning();
        } else {
          this.$dbg_console_log("Product card showing, will not resume search")
        }
      }
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