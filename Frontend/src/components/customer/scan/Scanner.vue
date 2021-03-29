<template>
  <div>

    <v-scandit class="v-scandit"
      license-key="ARlfIClBCaRqPjr3RSVODedD1xQ8Emxfw1wTGQxDSUR3aPAzxG5FNvFgi+q8cuauaFmUp49xwMNWftKHrFN6Ustffy0qeU7VDGlixw5Q22m0cLnKpSrwrAcuB9l/L6/rmjtLvLs5Azd55bEZYT+FNLiHoFQQEIz6KlMaL+xgrWO/ft1hVGZHYnI5YR3ERDjUIv6TnabFUPqsLHfaGqRrpByF2TJ2F/WKRTcVMKeqmXrPstJ67loe51BGkp6o0c9n2Y9ecZgBgqDdLeaUcaMyjkGvuJ3W/r555oUeogu+iTtzR04q2RQ5EUbgLWn0jhNKN3QewDCH6q9huSWxTnyDwaWqINIIFfhBD+zNIXXLn+nIBlzp4DeJwyn2cIKL5hBSIHtombNfRqQRmuaDVKh/QPCRvb4pXlsocEkiZY3Fm/s/XYIAB9eEj4dUpOio4brmZ1G0FShhKxX079+uA+9OewuEQQQcNz3KFeIw5ho/xddJ48dj639yb5PNNrCE3P3se4JMzAuW3QgOx87MERDLc/UFWzT3uRw9Ij/jdkoCHqxQO62O/PWiad8ZJb7XN7+xvuWDcgl54yzQT2ywiVZjvfyf7Lw64tgID4QkYvyJ4vzLzh0wQS5LfKDQJFP6tTyI2VbTXEAMjDq1F7Pv350VeuCgu/oqAo6RPmmn4/31WNd/IzgVsg9fzq6cOBMd0OKSA1xJm9Ib4eR8hiH3cuTuyRMNYYRKojstie6D+fr8DvCEhvbaMF6olZdQTpo1yP8WnR760nKRn5yzu31qLFZ6VRIz6VLaqd/RQ3+s9kiD6MAjlCyhpg==" 
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