
<template>
  <v-card>

    <v-card-title>
       <input v-model="myTextarea" :placeholder="getProduct.name">
    </v-card-title>

      <v-card-text>
        <div class="my-4 subtitle-1">
          Product Description:<br>
          <textarea v-model="myTextarea" :placeholder="getProduct.description"></textarea>
        </div>

        <div class="my-4 subtitle-1">
          Product Price: 
          $ <input v-model="myTextarea" :placeholder="getProduct.price">
        </div>

        <div class="my-4 subtitle-1">
          Sales Tax Rate: 
          <input v-model="myTextarea" :placeholder="getProduct.tax">%
        </div>
        
      </v-card-text>

  </v-card>
</template>


<script>
import {mapGetters} from 'vuex'
    // TODO: change all to editable fields to allow merchant to update
export default {
  props: ['barcode', 'clear'],
  data() {
    return {
      name: "Enter Product Name",
      description: "Enter Product Description",
      price: 0,
      tax: 0,
      myTextarea: [''] 
    }
  },
  computed: {
    ...mapGetters(['getProductsInStore']),
    getProduct: function() {
      const product = this.getProductsInStore.find(product => product.barcode == this.barcode)
      if (product === undefined) {
        return {
          barcode:'Failed to find product in Product.vue',
          name: "None",
          price: 0,
          tax: 0,
          description: "None",
          businessID: "None",
          quantity: 1,
        }
      }
      
      if (product.exists == true) {
        return product
      } else {
        return {
          name: "Enter Product Name",
          description: "Enter Product Description",
          price: 0,
          tax: 0,
        }

      }
      
    }
  },
  methods: {},
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