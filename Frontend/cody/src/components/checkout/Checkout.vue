<template>
  <v-card>
    <v-card-title> Total: ${{ getCartSubtotal+getCartTax }}  </v-card-title>
    <v-card-subtitle> Subtotal: ${{getCartSubtotal}} Tax: ${{getCartTax}}</v-card-subtitle>
    <v-card-actions class="justify-center">
      <v-btn to="/receipt">Checkout</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  computed: mapGetters(['getCartSubtotal', 'getCartTax'])
}
</script>

<style>

</style>