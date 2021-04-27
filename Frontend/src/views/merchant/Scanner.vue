<template>
  <v-container class="pa-0" id="merchant-scanner"> 
    <v-scandit class="v-scandit"
      license-key="AZHfIwp5KhyIO6iEgBErOm8kT1okINTyvW1FwOYVipCOLSC3RkUMO6dgy0cNV6sqCU+jkrcyH39NU+qhD1Kse5RdEW5hOcM9f3xNDI9/UV+OfEuyMUw89bNGFMClQsE3Olj/qNZlTrJmWYLt3SpNMkdBVwfRDAEjBAJGF4LyOrfT11Gi8v6m2rWRTmENwe7Q4TIwwnzAGCHXvjbmUHE7jXFQKRgCeiCxJ8NicdXliyBGzqzZfRbO27op9wDO4cX1wYOk8rVaNBgnLqn2oXida6s5/pnZRgMTRWTCqfOtASZvYZjdTHRZlyGb5aNhLZAvEqr0hJP1xGxfB7mYluWioai1hmnCju5dVATB6HdAbZiVazo2Y5rgohAZTuQZtFG67gfCmiP8u3iZgFcK7oDJO9OflmE4DwBB3Z76vDEiWnSH01HrtwX/0P+JahkHJxsRCkA3w120UJ6vBXenR6Q82xn9ioJOLKyntt/1FUCYiTXP0F+3NNfH4pzwgUnbjLwz9s4wWwU+6t4zRXRHU8tVbjYQ0ac0p2HOShNukRSqkhyzZ0vA2QsbT5mWNnyXC0cF8q7B2CEvXpizYSP45T1WfLdJhqWN3320nCYkHpOiaTDg85IzolZhQoT8B/6PrHXGV1KrhIc+DCmI9tf1vQvwoC6YaNVz+LERH720KHtSgJB/s/Q2LXaqeCOYG7RQBZSbDh0Rm+IxJJ/vt4g8K2o7op9glb8ySKjOe7/7qHrnUk+ZPoO2d4uO5rd0tWEFpzdTk8H/KEexzvAUSkWyUunKKt6ly5CRY4tYoqI+Vc/Euj5E/QuN1/wt/Xua6VZBlV6kPoYFqSQdHFSu0A==" 
      :configuration-options="{ engineLocation: 'https://cdn.jsdelivr.net/npm/scandit-sdk@5.x/build/' }"
      :scan-settings="{ enabledSymbologies: ['ean8', 'ean13', 'upca', 'upce']}"
      v-on:barcodePicker="(barcodePicker) => initPicker(barcodePicker)"
      v-on:scan="(barcode) => { findAndLoadProduct(barcode.barcodes[0].data) }" />
    <ScanSearchBar style="z-index:0;" v-on:showproduct="showItem" v-on:isSearching="toggleScanner($event)"/>
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
      id="product-editor"
    >
      <v-sheet>
        <v-card>
          <v-form 
            v-model="isProductFormValid"
            @submit.prevent="addScannedProduct">
            <v-card-title>
              <v-text-field
                v-model="name"
                :rules="nameRules"
                label="Name"
                id="name"
                validate-on-blur>
              </v-text-field>
            </v-card-title>
            <v-card-text>
              <v-textarea
                v-model="description"
                label="Description (optional)"
                id="description"
                counter
                maxlength=200>
              </v-textarea>
              <v-row>
                <v-col>
                  <v-text-field
                    v-model="price"
                    :rules="priceRules"
                    label="Price (USD)"
                    prefix='$'
                    type='tel'
                    id="price"
                    validate-on-blur>
                  </v-text-field>
                </v-col>
                <v-col>
                  <v-text-field
                    v-model="tax"
                    :rules="taxRules"
                    type='tel'
                    label="Tax"
                    suffix='%'
                    id="tax"
                    validate-on-blur>
                  </v-text-field>
                </v-col>
              </v-row>
            </v-card-text>
            <v-card-actions class="justify-center">
              <v-btn 
                id="delete-product"
                v-show="show_delete" 
                @click="deleteScannedProduct">
                Delete
                <v-icon right>mdi-delete</v-icon>
              </v-btn>
              <v-btn
                id="cancel-product"
                @click="cancelScannedProduct">
                Cancel
                <v-icon right>mdi-close</v-icon>
              </v-btn>
              <v-btn
                id="add-product"
                :disabled="!isProductFormValid"
                type="addScannedProduct">
                Add to Store
              </v-btn>
            </v-card-actions>
          </v-form>
        </v-card>
      </v-sheet>
    </v-bottom-sheet>
  </v-container>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'
import ScanSearchBar from '@/components/customer/scan/ScanSearchBar'

export default {
  components: {
    ScanSearchBar,
  },
  data() {
    return {
      name: "",
      nameRules:[
        v => !!v || 'Product name is required',
        v => v.length <= 60 || 'Product name is too long'
      ],
      description: "",
      price: "",
      priceRules:[
        v => !!v || 'Price is required',
        v => v >= 0 || 'Price must be non-negative',
        v => v.toString().length <= 60 || 'Price input is too large',
      ],
      tax: "",
      taxRules:[
        v => !!v || 'Tax is required (could be zero, check your local laws)',
        v => v >= 0 || 'Tax must be non-negative',
        v => v.toString().length <= 60 || 'Tax input is too large',
      ],
      isProductFormValid: false,

      clear_product_card_fields: false,
      show_scanned_product: false, // for displaying scanned product card
      show_popup: false,
      show_delete: false,
      scanned_product_barcode: 0,
      barcodePicker: null,
    };
  },
  computed: {
    ...mapGetters(['getProductsInStore']),
  },
  methods: {
    ...mapActions(["lookupProduct", "addProduct", "deleteProduct", "verifyToken"]),
    showItem (barcode) {
      console.log("Showing product: "+barcode)
      this.findAndLoadProduct(barcode)
    },
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
                console.log("product exists: "+result.product)
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
    toggleScanner(bool) {
      if (bool) {
        this.$dbg_console_log("Pause scanning for search")
        if(this.barcodePicker != null) {
          this.barcodePicker.pauseScanning();
          this.$dbg_console_log("scanning paused", this.barcodePicker);
        }
      } else {
        if (!this.show_scanned_product) {
          this.$dbg_console_log("Product card not showing, resuming scanning for search")
          this.barcodePicker.resumeScanning();
        } else {
          this.$dbg_console_log("Product card showing, will not resume search")
        }
      }
    },


      
  },
}
</script>

<style scoped>
  .v-scandit {
    position: absolute;
    z-index:-1;
    top: 0%;
    height: 100vh;
    width: 100%;
  }
</style>