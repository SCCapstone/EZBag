<template>
  <div>

    <v-scandit class="v-scandit"
      license-key="AZ8/Ug5hLjqmMAbzNgY90r9Cv7cjK9PQGVY+fCFQxRXOZLZ0AXBNwblHWITRd1iYdDQmhn1T/lBlYLkcNxURdVluSkcCPPA8qnHIkvJ/vHGcI8kIc1peT7hhv+wUYuDZAFSVGKNAW3YNeLmJLRgtdcgl2BEvIa/D5gQAFmYHWnTeObel0pyvJMwYV3YDv8DL35lmqB3ieAu4Db+OMGJD777/pGzvL7pEX0XucfhLiwuyvH2Gp7s0qQ3p/tEf6YFaIHhj+1MjAsKs+3UvWUqg9oUx0Amh+MLWwxqPSve7SjggQPMOKvdgvU8helgUrCk0ASff/p3lfzeJaVnel3TfJEf2FIzwn69JjsG//TmOgTwtYjVlsXzy5b7UoexWkwZbLanFR42yw2zgTKFiyKEXetFAd+FxMsAOAV84sW9gC05OmZI2NbEt0Y/XOiDcpDIHlvBRUJdliCkXlM2rDCInmU/FL8RZichbH7KrG8RN6Uy462MYVp5wx+vYcwQIwbClHfOFJFBuC47f9idh0xSYS/kPq9mkysjetBZWMGKxZxUiEmxRwQbukwUUz8XfSlgodYUvuyaoqKN46jaIeJlKLil9EFftjnzJBpjKHUFXLfvHqJ90NvI5YTsbOgwBL7sqmkqS6t7Ww+xankZsYkuAwjN/TlA0Tb+3dM8QOhRq1n35jLp01aBR9j6p4bQShY0mwtMAWetzy3uMKOJlGRw6l9ozzRQ62anydApDnQmd4WzhigIJJCKtCP6e1fRN1ZEDDHOkldgoN0nmxJ6R7T5gbm7TXopiYyVSSXEJUfoJ988jwNGTqnjJt144en6O7eBxeK5DEsEy//rPnA==" 
      :configuration-options="{ engineLocation: 'https://cdn.jsdelivr.net/npm/scandit-sdk@5.x/build/' }"
      :scan-settings="{ enabledSymbologies: ['ean8', 'ean13', 'upca', 'upce']}"
      v-on:barcodePicker="(barcodePicker) => initPicker(barcodePicker)"
      v-on:scan="(barcode) => { findAndLoadProduct(barcode.barcodes[0].data) }" />

    
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
              <v-card-text>This item could not be found in our database.</v-card-text>
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

          <v-card>
            <v-card-title>
              <input v-model="name" placeholder="Product name here...">
            </v-card-title>
              <v-card-text>
                <div class="my-4 subtitle-1">
                  Product Description:<br>
                  <textarea v-model="description" placeholder="Description here..."></textarea>
                </div>
                <div class="my-4 subtitle-1">
                  Product Price: 
                  $ <input v-model="price">
                </div>
                <div class="my-4 subtitle-1">
                  Sales Tax Rate: 
                  <input v-model="tax">%
                </div>    
              </v-card-text>
          </v-card>

          <v-card-actions class="justify-center">
            <v-btn 
              v-show="show_delete" 
              @click="deleteScannedProduct">Delete
              <v-icon right>mdi-delete</v-icon>
            </v-btn>
            <v-btn @click="cancelScannedProduct">Cancel
              <v-icon right>mdi-close</v-icon>
            </v-btn>
            <v-btn @click="addScannedProduct">Add to store
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-sheet>
    </v-bottom-sheet>
  </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'

export default {
  name: 'Merchant Scanner',
  components: {
    
  },
  data() {
    return {
      clear_product_card_fields: false,
      show_scanned_product: false, // for displaying scanned product card
      show_popup: false,
      show_delete: false,
      scanned_product_barcode: 0,
      barcodePicker: null,
      name: "test",
      description: "test",
      price: 0,
      tax: 0,
    };
  },
  computed: {
    ...mapGetters(['getProductsInStore']),
  },
  methods: {
    ...mapActions(["lookupProduct", "addProduct", "deleteProduct", "verifyToken"]),
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
        this.$dbg_console_log("scanning paused", this.barcodePicker)
      }
      // verify token valid before allowing user to edit product
      this.verifyToken().then( (result) => {
        if (result.status !== "failure") {
          this.$dbg_console_log("USER HAS VALID TOKEN")

          this.scanned_product_barcode = barcode

          this.lookupProduct({barcode:barcode, businessID:this.$route.params.id})
          .then((result) => {
              if (result.product === null) {
                console.log("product doesnt exist: "+this.name)
                this.show_delete = false
                this.name = ""
                this.description = ""
                this.price = 0
                this.tax = 0
              } else {
                console.log("product exists: "+result.product.name)
                this.show_delete = true
                this.name = result.product.name
                this.description = result.product.description
                this.price = result.product.price
                this.tax = result.product.tax
              }
              this.show_scanned_product = true

          }).catch(error => {
            this.$dbg_console_log(error)
            // if camera permissions are off, then barcode picker is null
            if(this.barcodePicker != null) 
              this.resetBarcodeScanner()
          })

        } else {
          this.$cookies.remove("token")
          this.$router.push("/login")
        }
      }).catch(error => {
        this.$dbg_console_log(error)
        // if camera permissions are off, then barcode picker is null
        if(this.barcodePicker != null) 
          this.resetBarcodeScanner()
      })

    },


    addScannedProduct() {
      console.log("adding scanned product to store")
      this.tax = this.tax/100
      // TODO: get actual barcode type from scanner
      this.addProduct({barcode:this.scanned_product_barcode, barcodeType:"ean", businessID:this.$route.params.id, 
                        name: this.name, description: this.description, price: this.price, tax: this.tax})
        .then((result) => {
            this.$dbg_console_log("backend result", result)
            console.log(result.status)
        }).catch(error => {
          this.$dbg_console_log(error)
        })
      // remove product from local store to allow query again
      this.hideScannedProductCard()
      // if camera permissions are off, then barcode picker is null
      if(this.barcodePicker != null) 
        this.resetBarcodeScanner()
    },
    // if user cancels adding the scanned product, we need to undo any changes they have made
    // if the product was loaded from the cart, we need to restore the product's quantity
    // if the product was not originally from the cart, we should remove it from the cart
    cancelScannedProduct() {
      this.hideScannedProductCard()
      // if camera permissions are off, then barcode picker is null
      if(this.barcodePicker != null) 
        this.resetBarcodeScanner()
    },
    deleteScannedProduct() {
      this.$dbg_console_log("Deleting product...")
      this.deleteProduct({barcode:this.scanned_product_barcode, businessID:this.$route.params.id})
        .then((result) => {
            if (!result.productDeleted) {
              this.show_popup = true
              this.$dbg_console_log("Failed to delete product!")
            } else {
              this.$dbg_console_log("Successfully deleted product")
            }
            this.hideScannedProductCard()
        }).catch(error => {
          this.$dbg_console_log(error)
          this.hideScannedProductCard()
        })      
      // if camera permissions are off, then barcode picker is null
      if(this.barcodePicker != null) 
        this.resetBarcodeScanner()
    },
    hideScannedProductCard() {
      this.clear_product_card_fields = true
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
      this.$dbg_console_log('resume scanning', this.barcodePicker)
    },


      
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