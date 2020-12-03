<template>
  <div>
    <v-quagga
      :onDetected="onScan"
      :readerSize="reader_size" 
      :aspectRatio="aspect_ratio"
      :readerTypes="['ean_reader','ean_8_reader', 'upc_reader', 'upc_e_reader']"
    ></v-quagga>
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

import jQuery from 'jquery'
global.jQuery = jQuery

export default {
  name: 'Scanner',
  components: {
    ScanButtons,
    Product
  },
  computed: mapGetters(['getCartSubtotal', 'getCart']),
  data() {
    return {
      show_scanned_product: false, // for displaying scanned product card
      scanned_product_barcode: null,
      initial_product_quantity: null,
      scanned_product_loaded_from_cart: false,
      reader_size: {
        width: 640,
        height: 200,
      },
      aspect_ratio: {
        min: 1,
        max: 2,
      },
      detecteds: [],
    };
  },
  methods:{
    ...mapActions([ "removeProduct",
                    "setProductQuantity",
                    "addProduct",
                  ]),
    onScan(scanData) {
      const barcode = scanData.codeResult.code;
      const barcodeType = scanData.codeResult.format;
      if (this.show_scanned_product) {
        console.log("Product scanned but product card already showing!")
      } else {
        this.scanned_product_barcode = barcode
        var product = this.getCart.find(product => product.barcode == barcode)
        // if product is in cart
        if(product!==undefined) {
          // product already in cart
          this.product_loaded_from_cart = true
          // save the state of the scanned product before allowing user to make changes
          this.initial_product_quantity = product.quantity
          this.show_scanned_product = true
        } else {
          // product not in cart
          // TODO: get businessID from store
          var businessID = "1";
          this.getProduct(barcode, businessID);
        }
        // TODO: implement function to pause scanner while show_scanned_product = true
      }
    },
    // if user cancels adding the scanned product, we need to undo any changes they have made
    // if the product was loaded from the cart, we need to restore the product's quantity
    // if the product was not originally from the cart, we should remove it from the cart
    hideScannedProductCard() {
      this.show_scanned_product = false
    },
    cancelScannedProduct() {
      this.hideScannedProductCard()
      if (this.product_loaded_from_cart === true) {
        this.setProductQuantity({barcode:this.scanned_product_barcode,
                                 amount:this.initial_product_quantity})
      }
      else
      {
        this.removeProduct({barcode:this.scanned_product_barcode}) 
      }
    },
    getProduct(barcode, businessID) {
      var data = {
        "barcode": barcode,
        "businessID": businessID
      };
      var ref = this
      data = JSON.stringify(data);
      jQuery.post(
          "http://localhost:8080/EZBagWebapp/webapi/lookup",
          data,
          function(data, status) {
              console.log(data)
              console.log("HTTP STATUS /webapi/lookup: "+status)
              if (data.status !== "failure") {
                ref.addProduct({barcode:data.barcode,
                  name:data.name,
                  price:data.price,
                  description:data.description,
                  businessID:data.businessID})
                ref.product_loaded_from_cart = false
              
                // save the state of the scanned product before allowing user to make changes
                ref.initial_product_quantity = data.quantity
                ref.show_scanned_product = true
              } else {
                // TODO: elegantly display temporary poppup indicating product not found
                alert("Scanned product not found, please try again!");
                console.log("Scanned product not found, please try again!")
              }
              // TODO: handle case that data is not found response
              
          }
        );
    }

  }
}
</script>