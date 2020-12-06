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
        <v-card-actions>
          <v-row align="center" justify="center">
            <v-col>
              <v-btn @click="cancelScannedProduct" icon>
                <v-icon>mdi-close-circle</v-icon>
              </v-btn>
            </v-col>
            <v-col>
              <v-btn @click="hideScannedProductCard" icon>
                <v-icon>mdi-check</v-icon>
              </v-btn>
            </v-col>
          </v-row>
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
    var ref = this
    ScanditSDK.configure("AUZPWwnfFoljEUPsdi9NGuRA5C5zF7MWiH2ATlBzzRoUMTzzCyz2eIsc4K/IXEg79zR0P/MyBvLGWYeasF4Mb8dy0uW0N2YJP129zwZy2Lp0UWEdXmEzKCRdM8SaeS63XlnLFaZKvjQUeTBPYgcylbkVWWH6KcrqvBrisCWjchbaBjIkY1Jg04dgfp+bv0AEn1+Zx2hUKE94qzXxHXGos8w4n7JOgigJVYbC4xgWS6g/3WmXebi22sBQh3gxkQqGEsfKC5XLPPPXS4ZM3GQR8Yih2iTOe0F88gkbwcQj86EMURTe6/3o7iobP13CS7DkIBJX2kq/i/fXbPrr0Hey46yRJM7z5TaZeMbQV9j+fe9TNBPlsNdZ1HOVwjKgYqxBSqNBuBJ8K7gGz+yaV8vkV+xVGroIWNfolxt5BQeRrX+aqFc65NnfSuYyMds01a5M0Mj8cXwWwBJCifFpLPE02KrZkOmJ/gYC+un1qGbjkn1y0sbXdrhun5utExw6atXKvfQ04WOI/jwTSmdzo6RtMqdFUit5vhls8x1aPkPUWSVmpOQC2ot+CifFfW19ltPhklmNryRAot8Nso3AMkzbMW9h1sMb/gFyf8eIP4Zcs5BsnWNDVKGJrSyznv5JlJpQQiGdyb6QVa5F0sqt4KUT9uiquaMDVjxeDusuWPgv3pooHDre/Rf34ffbtEd7uWcuF7wLqZ6BbnNSA6BMv45Sh3tZ4v1arG7NGNSZUZKg23qUiPoP+XvcmbAtHcN4vfZCaywUj60pv916l4/0DXrlqRFnA5aSx/yKh4Evu4PgROYieHH2Vd67HnePg1Dq5FMZkySgfkagORlufg==", {
      engineLocation: "https://cdn.jsdelivr.net/npm/scandit-sdk@5.x/build/",
    })
      .then(() => {
        return ScanditSDK.BarcodePicker.create(document.getElementById("scandit-barcode-picker"), {
          // enable some common symbologies
          videoFit: ScanditSDK.BarcodePicker.ObjectFit.COVER,
          scanSettings: new ScanditSDK.ScanSettings({ enabledSymbologies: ["ean8", "ean13", "upca", "upce"] }),
        });
      })
      .then((barcodePicker) => {
        // barcodePicker is ready here, show a message every time a barcode is scanned
        barcodePicker.on("scan", (scanResult) => {
          var barcode = scanResult.barcodes[0].data
          if (this.show_scanned_product==false && this.scanned_product_barcode !== barcode) {
            console.log("Scanning")
            this.scanned_product_barcode = barcode
            ref.onScan(barcode);
          } else {
            console.log("Product scanned but product card already showing!")
          }
        });
      });
  },
  methods:{
    ...mapActions([ "removeProduct",
                    "setProductQuantity",
                    "addProduct",
                  ]),
    onScan(barcode) {

      this.show_scanned_product = true
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
      // TODO: implement function to pause scanner while show_scanned_product = true
      
    },
    // if user cancels adding the scanned product, we need to undo any changes they have made
    // if the product was loaded from the cart, we need to restore the product's quantity
    // if the product was not originally from the cart, we should remove it from the cart
    hideScannedProductCard() {
      this.show_scanned_product = false;
      this.scanned_product_barcode = null;
    },
    cancelScannedProduct() {
      if (this.product_loaded_from_cart == true) {
        console.log('line129:',this.initial_product_quantity)
        this.setProductQuantity({barcode:this.scanned_product_barcode,
                                 amount:this.initial_product_quantity})
      }
      else
      {
        console.log('Attempting to remove:',this.scanned_product_barcode)
        this.removeProduct({barcode:this.scanned_product_barcode}) 
      }
      this.hideScannedProductCard()
    },
    getProduct(barcode, businessID) {
      console.log('line139:',typeof(barcode))
      var data = {
        "barcode": barcode,
        "businessID": businessID
      };
      var ref = this
      data = JSON.stringify(data);
      // TODO: handle api call failed case elegantly 
      jQuery.post(
          "/EZBagWebapp/webapi/lookup",
          data,
          function(data, status) {
            // handle json object return as string
            if (typeof(data) == "string")
              data = JSON.parse(data)
            console.log(data)
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