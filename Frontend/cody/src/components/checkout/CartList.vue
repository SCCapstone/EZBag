<template>
  <div>
    <v-container class="products">
      <v-row
        v-for="product in getCart"
        v-bind:key="product.barcode"
      >
        <v-col>
          <Product 
          v-bind:barcode=product.barcode
          v-bind:name=product.name
          v-bind:price=product.price
          v-bind:description=product.description
          v-bind:businessID=product.businessID
          v-bind:quantity=product.quantity
          />
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
  computed: mapGetters(['getCart', 'getSubtotal'])
}
</script>
