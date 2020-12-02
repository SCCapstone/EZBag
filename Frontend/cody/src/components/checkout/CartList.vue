<template>
  <div>
    <v-container class="CartList">
      <v-row
        v-for="product in getCart"
        v-bind:key="product.barcode"
      >
        <v-col>
          <Product v-bind:barcode=product.barcode />
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
// https://vuex.vuejs.org/guide/actions.html#dispatching-actions-in-components
import { mapGetters } from 'vuex';
import Product from '@/components/checkout/Product'

export default {
  name:"CartList",
  components: {
    Product
  },
  computed: mapGetters(['getCart'])
}
</script>
