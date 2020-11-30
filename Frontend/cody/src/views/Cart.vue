<template>
  <v-container class="Cart">
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
          <Checkout />
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
    Checkout
  },
  data() {
    return {
      show_checkout: false
    };
  }
};
</script>
