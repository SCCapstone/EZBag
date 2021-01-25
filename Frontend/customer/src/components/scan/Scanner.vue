<template>
  <div id='barcodePickerOrigin'>
    <div id="scandit-barcode-picker"></div>
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
          return ScanditSDK.BarcodePicker.create(document.getElementById("scandit-barcode-picker"), {
          videoFit: ScanditSDK.BarcodePicker.ObjectFit.COVER,
        }).then((picker) => {
          barcodePicker = picker;// access to barcode picker outside of ScanditSDK call
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
              var barcode = scanResult.barcodes[0].data
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

      var product = this.getCart.find(product => product.barcode == barcode)
      // if product is in cart
      if(product!==undefined) {
        // product already in cart
        this.product_loaded_from_cart = true
        // save the state of the scanned product before allowing user to make changes
        this.initial_product_quantity = product.quantity
      } else {
        // product not in cart
        this.product_loaded_from_cart = false
        this.getProduct(barcode, this.getCartBusinessID);
      }
      this.scanned_product_barcode = barcode
      this.show_scanned_product = true
    },
    // if user cancels adding the scanned product, we need to undo any changes they have made
    // if the product was loaded from the cart, we need to restore the product's quantity
    // if the product was not originally from the cart, we should remove it from the cart
    hideScannedProductCard() {
      this.show_scanned_product = false;
      this.scanned_product_barcode = null;
      console.log('resume scanning', barcodePicker)
      barcodePicker.resumeScanning();
    },
    cancelScannedProduct() {
      if (this.product_loaded_from_cart == true) 
        this.setProductQuantity({barcode:this.scanned_product_barcode, amount:this.initial_product_quantity})
      else
        this.removeProduct({barcode:this.scanned_product_barcode}) 
      this.hideScannedProductCard()
    },
    getProduct(barcode, businessID) {
      var data = {
        "barcode": barcode,
        "businessID": businessID
      };
      var ref = this
      data = JSON.stringify(data);
      // TODO: handle api call failed case elegantly 
      jQuery.post(
          process.env.VUE_APP_ROOT_API+"EZBagWebapp/webapi/lookup",
          data,
          function(data, status) {
            // handle json object return as string
            if (typeof(data) == "string")
              data = JSON.parse(data)
            if (status == "success" && data.status !== "failure") {
              ref.addProduct({barcode:data.barcode,
                              name:data.name,
                              price: data.price,
                              tax: data.tax,
                              description:data.description,
                              businessID:businessID})
            } else {
              // TODO: elegantly display temporary poppup indicating product not found
              ref.addProduct({barcode:barcode,
              name:"Unknown product ".concat(barcode),
              price: 10,
              tax: 0.30,
              description:"Not in our database",
              businessID:businessID})
            }
          }
        );
    }

  },
}
</script>

<style scoped>
  #barcodePickerOrigin {
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