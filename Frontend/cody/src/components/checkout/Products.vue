<template>
  <div>
    <v-container class="products">
      <v-row
      v-for="product in allProducts"
      v-bind:key="product.barcode"
      >
        <v-col
        class="flex-grow-1 flex-shrink-0"
        >
          {{ product.name }}
        </v-col>
        <v-col
        class="flex-grow-0 flex-shrink-1"
        >
          ${{ product.price }}
        </v-col>

        <v-col
        class="flex-grow-0 flex-shrink-1">
          <v-icon @click="deleteProduct(product.barcode)">mdi-delete</v-icon>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';

export default {
  name:"Products",
  methods: {
    ...mapActions(["deleteProduct"])
  },
  computed: mapGetters(['allProducts'])
}
</script>

<style>

</style>