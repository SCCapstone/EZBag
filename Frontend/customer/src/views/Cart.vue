<template>
  <v-container>
    <div v-show="show_popup==true">
      <v-row justify="center">
        <v-dialog
          v-model="dialog"
          persistent
          max-width="290"
        >
          <v-card>
            <v-card-title class="headline">
              Your cart is empty!
            </v-card-title>
            <v-card-text>Please add items to your cart before attempting to checkout.</v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                justify="center"
                color="green darken-1"
                text
                @click="dialog = false"
              >
                OK
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
  </v-row>
    </div>
    <CartList />
    <v-bottom-sheet
      v-model="show_checkout"
      inset
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          v-bind="attrs"
          v-on="on"
          fab
          fixed bottom
          id="checkout-btn"
          :style="{left: '50%', transform:'translateX(-50%)'}"
        >
          <v-icon>mdi-currency-usd</v-icon>
        </v-btn>
      </template>
      <v-sheet
        class="text-center"
      >
        <v-btn
          @click="show_checkout = !show_checkout"
        >close</v-btn>
          <Checkout v-on:show-popup="showPopUp"/>
      </v-sheet>
    </v-bottom-sheet>
  </v-container>
</template>

<script>
// @ is an alias to /src
import CartList from '@/components/checkout/CartList'
import Checkout from '@/components/checkout/Checkout'

export default {
  name: 'Cart',

  components: {
    CartList,
    Checkout,
  },
  data: () => ({
    show_checkout: false,
    show_popup: false,
    dialog: false
  }),
  methods: {
    showPopUp() {
      this.$emit("showPopUp")
      this.dialog = true
    },
  }
};
</script>
