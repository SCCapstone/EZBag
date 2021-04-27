<template>
  <div id='customer-scanner'>

    <v-scandit
      class="v-scandit"
      license-key="AZHfIwp5KhyIO6iEgBErOm8kT1okINTyvW1FwOYVipCOLSC3RkUMO6dgy0cNV6sqCU+jkrcyH39NU+qhD1Kse5RdEW5hOcM9f3xNDI9/UV+OfEuyMUw89bNGFMClQsE3Olj/qNZlTrJmWYLt3SpNMkdBVwfRDAEjBAJGF4LyOrfT11Gi8v6m2rWRTmENwe7Q4TIwwnzAGCHXvjbmUHE7jXFQKRgCeiCxJ8NicdXliyBGzqzZfRbO27op9wDO4cX1wYOk8rVaNBgnLqn2oXida6s5/pnZRgMTRWTCqfOtASZvYZjdTHRZlyGb5aNhLZAvEqr0hJP1xGxfB7mYluWioai1hmnCju5dVATB6HdAbZiVazo2Y5rgohAZTuQZtFG67gfCmiP8u3iZgFcK7oDJO9OflmE4DwBB3Z76vDEiWnSH01HrtwX/0P+JahkHJxsRCkA3w120UJ6vBXenR6Q82xn9ioJOLKyntt/1FUCYiTXP0F+3NNfH4pzwgUnbjLwz9s4wWwU+6t4zRXRHU8tVbjYQ0ac0p2HOShNukRSqkhyzZ0vA2QsbT5mWNnyXC0cF8q7B2CEvXpizYSP45T1WfLdJhqWN3320nCYkHpOiaTDg85IzolZhQoT8B/6PrHXGV1KrhIc+DCmI9tf1vQvwoC6YaNVz+LERH720KHtSgJB/s/Q2LXaqeCOYG7RQBZSbDh0Rm+IxJJ/vt4g8K2o7op9glb8ySKjOe7/7qHrnUk+ZPoO2d4uO5rd0tWEFpzdTk8H/KEexzvAUSkWyUunKKt6ly5CRY4tYoqI+Vc/Euj5E/QuN1/wt/Xua6VZBlV6kPoYFqSQdHFSu0A==" 
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