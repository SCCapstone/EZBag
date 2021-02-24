<template>
  <div>
    <v-scandit 
      license-key="AcN/DBgYOL7AKEDqcipZkFZAlsxfPT8wWnSu1HF/oAPwSPPNtlDJw/RzfunnSuo0fVUK1YUao28BYQ+vHUE86/8JaHQET5B1h3rJ8rRk8UcRdXx5pCBhz5QF5Fn1ODWosRhAJzNM0hxpXmSD2OjbpljflTYHVmzyCcIQTMsEAasUgE2cMu1Odx+TCUcZVIBvndjZdI473l4AEjl4VSZlzi/Iw103XcaWRj85gCTtFGRhw01sd356F8eoxvLGfAqS71GGiICahkk3GGm685hpup1myHzM7KB51cWksy2VCSmSLVHgprAlsDDihIaLEFBiJXpP1c0/6cGcE0Z2pHHNlSDr3WNnvT/A1EJaKrFSgZp8jqNnSYD9sqFHnuWazvY/lzc6CWNhG8L1OQ35HpLw+XFL/PB/qOrqfFj6boVaXvqEYFiCQZNgAhmkZivdumTROV8lWuP+BVypLD2pHvbm1iDcDmkXTWQhRymYqY8WuBqEINYMaYKny3exWhIoHW6rIofsjtshAZUZB+abul36dvv2STnU16tsG9/4Edibh0hnfUk5U8Cq/w/WeWGMhAosUpNCwUh1mOPpznJBDJr/kl5FkahLuBUsh40IMpltrleFfB0+7CuUFsJTxL0zKis7Xz7xgR4AGY8WmIKBnlgOYmSGO4TojK8fAVyuVfnWmg+uXrAucsCDpRlDPvrTnAk1XoyI3NZKif2rWl7ksglaVntNX3tNeXMObUKGKlQZfCvb4VnciPUe+OxUcd8GmOW9c830fR9sjkSu/m+E18OZNlSP1uq8dWDe/kEw2ByiNFp1fSBMWQ==" 
      :configuration-options="{ engineLocation: 'https://cdn.jsdelivr.net/npm/scandit-sdk@5.x/build/' }"
      :scan-settings="{ enabledSymbologies: ['ean8', 'ean13', 'upca', 'upce']}"
      v-on:barcodePicker="(barcodePicker) => initPicker(barcodePicker)"
      v-on:scan="(barcode) => { onScan(barcode.barcodes[0].data) }" />
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
    >
      <v-sheet>
        <v-card>
          <Product 
          v-bind:barcode="scanned_product_barcode"
          v-bind:elevation="0"
          v-on:removed-product="hideScannedProductCard"
          />
          <v-card-actions class="justify-center">
            <v-btn @click="cancelScannedProduct">Remove
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
import Product from '@/components/customer/checkout/Product'


export default {
  name: 'Scanner',
  components: {
    ScanButtons,
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
  .scandit scandit-container {
    height: 100%;
    width: 100%;
  }
  v-scandit {
    position: absolute;
    top: 0%;
    margin: auto;
    height: 100%;
    width: 100%;
  }
</style>