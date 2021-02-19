<template>
  <div>
    <div id="scan-content">
      <div id="scandit-barcode-picker"></div>
      <ScanButtons 
        v-bind:total=getSubtotal />
    </div>
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
import ScanButtons from '@/components/scan/ScanButtons'
import Product from '@/components/checkout/Product'
import * as ScanditSDK from "scandit-sdk";

let barcodePicker;

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
      initial_product_quantity: 0,
      scanned_product_barcode: 0
    };
  },
  mounted () {
    // https://github.com/Scandit/barcodescanner-sdk-for-web-samples/blob/master/vanilla-samples/proof-of-delivery/index.html
    ScanditSDK.configure("AcN/DBgYOL7AKEDqcipZkFZAlsxfPT8wWnSu1HF/oAPwSPPNtlDJw/RzfunnSuo0fVUK1YUao28BYQ+vHUE86/8JaHQET5B1h3rJ8rRk8UcRdXx5pCBhz5QF5Fn1ODWosRhAJzNM0hxpXmSD2OjbpljflTYHVmzyCcIQTMsEAasUgE2cMu1Odx+TCUcZVIBvndjZdI473l4AEjl4VSZlzi/Iw103XcaWRj85gCTtFGRhw01sd356F8eoxvLGfAqS71GGiICahkk3GGm685hpup1myHzM7KB51cWksy2VCSmSLVHgprAlsDDihIaLEFBiJXpP1c0/6cGcE0Z2pHHNlSDr3WNnvT/A1EJaKrFSgZp8jqNnSYD9sqFHnuWazvY/lzc6CWNhG8L1OQ35HpLw+XFL/PB/qOrqfFj6boVaXvqEYFiCQZNgAhmkZivdumTROV8lWuP+BVypLD2pHvbm1iDcDmkXTWQhRymYqY8WuBqEINYMaYKny3exWhIoHW6rIofsjtshAZUZB+abul36dvv2STnU16tsG9/4Edibh0hnfUk5U8Cq/w/WeWGMhAosUpNCwUh1mOPpznJBDJr/kl5FkahLuBUsh40IMpltrleFfB0+7CuUFsJTxL0zKis7Xz7xgR4AGY8WmIKBnlgOYmSGO4TojK8fAVyuVfnWmg+uXrAucsCDpRlDPvrTnAk1XoyI3NZKif2rWl7ksglaVntNX3tNeXMObUKGKlQZfCvb4VnciPUe+OxUcd8GmOW9c830fR9sjkSu/m+E18OZNlSP1uq8dWDe/kEw2ByiNFp1fSBMWQ==", {
      engineLocation: "https://cdn.jsdelivr.net/npm/scandit-sdk@5.x/build",
      })
        .then(() => {
          return ScanditSDK.BarcodePicker
                  .create(document.getElementById("scandit-barcode-picker"))
                  .then((picker) =>
          {
          barcodePicker = picker;// access to barcode picker outside of ScanditSDK call
          // set scandit settings https://docs.scandit.com/stable/web/classes/barcodepicker.html
          picker.setMirrorImageEnabled(false);
          picker.setVideoFit(ScanditSDK.BarcodePicker.ObjectFit.COVER);
          picker.setGuiStyle(ScanditSDK.BarcodePicker.GuiStyle.NONE);
          barcodePicker.applyScanSettings(
            new ScanditSDK.ScanSettings({
              enabledSymbologies: [
                ScanditSDK.Barcode.Symbology.UPCA,
                ScanditSDK.Barcode.Symbology.UPCE,
                ScanditSDK.Barcode.Symbology.EAN8,
                ScanditSDK.Barcode.Symbology.EAN13
              ],
              codeDuplicateFilter: -1,
            })
          );
          barcodePicker
            .on("scan", (scanResult) => {
              barcodePicker.pauseScanning()
              this.$dbg_console_log("paused scanning", barcodePicker)
              var barcode = scanResult.barcodes[0].data
              this.$dbg_console_log("read barcode", barcode)
              this.onScan(barcode)
            })
            .on("scanError", console.error);
        });
    })
    .catch(console.error);
  },
  computed: mapGetters(['getSubtotal', 'getBusinessID']),
  methods:{
    ...mapActions(["addProductToCart"]),
    ...mapMutations(["removeProductFromCart",
                      "setProductQuantity",
                    ]),
    onScan(barcode) {
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
      barcodePicker.resumeScanning();
      barcodePicker.clearSession();
      this.$dbg_console_log('resume scanning', barcodePicker)
    }
  },
}
</script>

<style scoped>
  #scan-content {
    height: 100%;
    width: 100%;
  }
  #scandit-barcode-picker {
    position: absolute;
    top: 0%;
    margin: auto;
    height: 100%;
    width: 100%;
  }
</style>