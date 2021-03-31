<template>
  <v-card>
    <v-card-title class="text-center"> Total: ${{ showTwoDecimal(getSubtotal + getTax) }}  </v-card-title>
    <v-card-subtitle class="text-center"> Subtotal: ${{showTwoDecimal(getSubtotal)}} Tax: ${{showTwoDecimal(getTax)}}</v-card-subtitle>
    <v-card-actions class="justify-center">
      <v-btn @click="checkout">Checkout</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';

export default {
  computed: mapGetters(['getSubtotal', 'getTax']),
  methods: {
    ...mapActions(['checkoutCart']),
    showTwoDecimal(num) {		
      return (num).toFixed(2);
    },
    checkout() {
      console.log("subtotal: "+this.getSubtotal)
      console.log("tax: "+this.getTax)
      this.checkoutCart(this.$route.params.id)
        .then((result) => { // backend succesfully responded
          this.$dbg_console_log('Attempted to checkout', result)
          if(result.checkoutSuccesful && result.cartEmpty)
            this.$router.push('receipt') // succesful checkout
          else if(!result.checkoutSuccesful && result.cartEmpty)
            this.$emit("show-popup") // cannot checkout empty cart
          else if(!result.checkoutSuccesful && !result.cartEmpty)
            this.$dbg_console_log('Backend could not checkout cart for some reason')
        }).catch(error => { // backend could not be reached
          this.$dbg_console_log('Failed to checkout', error)
        })
    }
  }
}
</script>

<style>

</style>