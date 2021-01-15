<template>
  <v-card>
    <v-card-title class="text-center"> Total: ${{ getCartSubtotal+getCartTax }}  </v-card-title>
    <v-card-subtitle class="text-center"> Subtotal: ${{getCartSubtotal}} Tax: ${{getCartTax}}</v-card-subtitle>
    <v-card-actions class="justify-center">
      <v-btn @click="sendCartToBackend">Checkout</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
import jQuery from 'jquery'

export default {
  computed: mapGetters(['getCart', 'getCartSubtotal', 'getCartTax', 'getCartBusinessID', 'getSessionID']),
  methods: {
    ...mapActions(['setCartHash']),
    sendCartToBackend() {
      if (this.getCart.length > 0) {
        var data = {
          barcodes: this.getCart.map(product => product.barcode),
          quantities: this.getCart.map(product => product.quantity),
          session: this.getSessionID,
          businessID: this.getCartBusinessID
        }
        console.log('cart before stringify', data)
        data = JSON.stringify(data)
        console.log('cart before sending:', data)
        var ref = this
        jQuery.post(
          "/EZBagWebapp/webapi/cart",
          data,
          function(data, status) {
            // handle json object return as string
            if (typeof(data) == "string")
              data = JSON.parse(data)
            if (status == "success" && data.status !== "failure") {
              console.log("Successfully submitted cart")
              ref.setCartHash({cartHash:data.hash})
              ref.$router.push('receipt');
            } else {
              console.log("Failed to submit cart to backend")
              alert("Checkout failed, please try again")
            }
          }
        );
      } else {
        // TODO: add mor elegant empty cart message
        alert("Please add an item to your cart checking out!")
      }
      
    }
  }
}
</script>

<style>;

</style>