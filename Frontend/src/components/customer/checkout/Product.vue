<template>
  <v-card>
    <v-card-title>{{ getProduct.name }}</v-card-title>
    <v-card-actions>
      <span class="subheading ml-2"> ${{ getProduct.price }}</span>
      <v-spacer></v-spacer>
      <!-- Remove product button - conditionally rendered-->
      <v-btn 
        @click="removeProductFromCart(barcode)"
        v-show="getProduct.quantity === 1"
        v-on:click="$emit('removed-product')"
        icon
      >
      <!-- Decrement product quantity - conditionally rendered on product.quantity-->
        <v-icon>mdi-delete</v-icon>
      </v-btn>
      <v-btn 
        @click="setProductQuantity({barcode:barcode,typeOrAmount:'DECREMENT'})"
        v-show="getProduct.quantity !== 1" 
        icon
      >
        <v-icon>mdi-minus</v-icon>
      </v-btn>
      <!-- Item quantity -->
      <!-- TODO: make this a button, which can be clicked to edit the quantity directly-->
      <span class="subheading mr-2 ml-2"> {{ getProduct.quantity }}</span>
      <!-- Increment product quantity -->
      <v-btn @click="setProductQuantity({barcode:barcode,typeOrAmount:'INCREMENT'})" icon>
        <v-icon>mdi-plus</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapMutations, mapGetters } from 'vuex'

export default {
  props: ['barcode'],
  computed: {
    ...mapGetters(['getProductsInCart']),
    getProduct: function() {
      const product = this.getProductsInCart.find(product => product.barcode == this.barcode)
      if (product===undefined)
        return {
                barcode:'Failed to find product in Product.vue',
                name: "None",
                price: 0,
                tax: 0,
                description: "None",
                businessID: "None",
                quantity: 1,
                }
      return product
    }
  },
  methods: {
  ...mapMutations([ 'removeProductFromCart', 'setProductQuantity'])
  },
}
</script>

<style scoped>

/*
  Fix for word-wrapping in title. To be fixed in Vuetify 3.
  https://github.com/vuetifyjs/vuetify/issues/9130#issuecomment-542534966
*/
.v-card__text, .v-card__title {
  word-break: normal;
}

</style>