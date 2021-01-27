<template>
  <div>
    <div id="scan-content">
      <div id="scandit-barcode-picker"></div>
      <ScanButtons 
        v-bind:total=getCartSubtotal />
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
import { mapGetters, mapActions} from 'vuex';
import ScanButtons from '@/components/scan/ScanButtons'
import Product from '@/components/checkout/Product'
import * as ScanditSDK from "scandit-sdk";

let barcodePicker;

import jQuery from 'jquery'
global.jQuery = jQuery

export default {
  name: 'Scanner',
  components: {
    ScanButtons,
    Product
  },
  computed: mapGetters(['getCartSubtotal', 'getCart', 'getCartBusinessID']),
  data() {
    return {
      show_scanned_product: false, // for displaying scanned product card
      scanned_product_barcode: null,
      initial_product_quantity: null,
      scanned_product_loaded_from_cart: false,
      scanned_barcode: null,
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
              barcodePicker.pauseScanning();
              console.log("paused scanning", barcodePicker)
              var barcode = scanResult.barcodes[0].data
              console.log("read barcode", barcode)
              this.onScan(barcode);
            })
            .on("scanError", console.error);
        });
    })
    .catch(console.error);
  },
  methods:{
    ...mapActions([ "removeProduct",
                    "setProductQuantity",
                    "addProduct",
                  ]),
    onScan(barcode) {
      this.scanned_product_barcode = barcode
      // attempt to find product in cart
      var product = this.getCart.find(product => product.barcode == barcode)
      if(product!==undefined) { // product was already in cart
        // save the state of the scanned product before allowing user to make changes
        this.product_loaded_from_cart = true
        this.initial_product_quantity = product.quantity
        this.show_scanned_product = true
      } else { // product was not in cart
        this.product_loaded_from_cart = false
        // request product information from backend
        this.requestProductFromBackend(barcode, this.getCartBusinessID);
      }
    },
    // if user cancels adding the scanned product, we need to undo any changes they have made
    // if the product was loaded from the cart, we need to restore the product's quantity
    // if the product was not originally from the cart, we should remove it from the cart
    hideScannedProductCard() {
      this.show_scanned_product = false;
      this.scanned_product_barcode = null;
      // https://docs.scandit.com/stable/web/classes/barcodepicker.html#clearsession
      barcodePicker.resumeScanning();
      barcodePicker.clearSession();
      console.log('resume scanning', barcodePicker)
    },
    cancelScannedProduct() {
      if (this.product_loaded_from_cart == true) 
        this.setProductQuantity({barcode:this.scanned_product_barcode, amount:this.initial_product_quantity})
      else
        this.removeProduct({barcode:this.scanned_product_barcode}) 
      this.hideScannedProductCard()
    },
    requestProductFromBackend(barcode, businessID) {
      var data = {
        "barcode": barcode,
        "businessID": businessID
      };
      data = JSON.stringify(data);
      // TODO: handle api call failed case elegantly 
      var self = this;
      jQuery.post(
          process.env.VUE_APP_ROOT_API+"EZBagWebapp/webapi/lookup",
          data,
          function(data, status) {
            // handle json object return as string
            if (typeof(data) == "string")
              data = JSON.parse(data)
            if (status == "success" && data.status !== "failure") { 
              let product = {barcode:data.barcode,
                      name:data.name,
                      price: data.price,
                      tax: data.tax,
                      description:data.description,
                      businessID:businessID};
              self.addProduct(product)
              self.show_scanned_product = true
            } else { // product was not found by backend
              console.log("product was not found by backend")
              // https://docs.scandit.com/stable/web/classes/barcodepicker.html#clearsession
              barcodePicker.resumeScanning();
              barcodePicker.clearSession();
            }
          }
        );
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