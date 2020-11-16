<template>
  <div>
    <v-container class="products">
      <v-row
      v-for="product in getCart"
      v-bind:key="product.barcode"
      >
        <v-col class="flex-grow-1 flex-shrink-0">
          {{ product.name }}
        </v-col>
        <v-col class="flex-grow-0 flex-shrink-1">
          ${{ product.price }}
        </v-col>
        <v-col class="flex-grow-0 flex-shrink-1">
          <v-icon @click="removeProduct(product.barcode)">mdi-delete</v-icon>
        </v-col>
        <!-- TODO: add increment/decrement (adjust) quantity buttons -->
      </v-row>
    </v-container>
  </div>
</template>

<script>
// https://vuex.vuejs.org/guide/actions.html#dispatching-actions-in-components
import { mapGetters, mapActions } from 'vuex';

export default {
  name:"Cart",
  methods: {
    ...mapActions(["addProduct",
                   "removeProduct",
                   "setProductQuantity",
                   "adjustProductQuantity"
                  ])
  },
  computed: mapGetters(['getCart'])
}
</script>

<style>

</style>