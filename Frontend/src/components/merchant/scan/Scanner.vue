<template>
  <div>

    <v-scandit class="v-scandit"
      license-key="ARlfIClBCaRqPjr3RSVODedD1xQ8Emxfw1wTGQxDSUR3aPAzxG5FNvFgi+q8cuauaFmUp49xwMNWftKHrFN6Ustffy0qeU7VDGlixw5Q22m0cLnKpSrwrAcuB9l/L6/rmjtLvLs5Azd55bEZYT+FNLiHoFQQEIz6KlMaL+xgrWO/ft1hVGZHYnI5YR3ERDjUIv6TnabFUPqsLHfaGqRrpByF2TJ2F/WKRTcVMKeqmXrPstJ67loe51BGkp6o0c9n2Y9ecZgBgqDdLeaUcaMyjkGvuJ3W/r555oUeogu+iTtzR04q2RQ5EUbgLWn0jhNKN3QewDCH6q9huSWxTnyDwaWqINIIFfhBD+zNIXXLn+nIBlzp4DeJwyn2cIKL5hBSIHtombNfRqQRmuaDVKh/QPCRvb4pXlsocEkiZY3Fm/s/XYIAB9eEj4dUpOio4brmZ1G0FShhKxX079+uA+9OewuEQQQcNz3KFeIw5ho/xddJ48dj639yb5PNNrCE3P3se4JMzAuW3QgOx87MERDLc/UFWzT3uRw9Ij/jdkoCHqxQO62O/PWiad8ZJb7XN7+xvuWDcgl54yzQT2ywiVZjvfyf7Lw64tgID4QkYvyJ4vzLzh0wQS5LfKDQJFP6tTyI2VbTXEAMjDq1F7Pv350VeuCgu/oqAo6RPmmn4/31WNd/IzgVsg9fzq6cOBMd0OKSA1xJm9Ib4eR8hiH3cuTuyRMNYYRKojstie6D+fr8DvCEhvbaMF6olZdQTpo1yP8WnR760nKRn5yzu31qLFZ6VRIz6VLaqd/RQ3+s9kiD6MAjlCyhpg==" 
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