<template>
  <v-container class="CartVerification">
    <ListofCarts />
    <template>
    <v-row
        v-for="carts in getListofCarts"
        v-bind:key="Cart.CartList"
    >
    <v-column
        <Cart = v-bind:CartList=Cart.CartList \:>
    >
    </v-column>
    </v-row>
    </template>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex';
import Product from '@/components/checkout/Product'

export default {
  name: 'CartVerification',
  components: {
      ListofCarts
    },
  computed: mapGetters(['getListofCarts']),
  data() {
    return {
        ListofCarts
    };
  }
};
</script>