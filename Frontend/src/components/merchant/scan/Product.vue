
<template>
  <v-card>

    <v-card-title>
       <input :value="getProduct.name" placeholder="Product name here...">
    </v-card-title>

      <v-card-text>
        <div class="my-4 subtitle-1">
          Product Description:<br>
          <textarea :value="getProduct.description" placeholder="Description here..."></textarea>
        </div>

        <div class="my-4 subtitle-1">
          Product Price: 
          $ <input :value="getProduct.price">
        </div>

        <div class="my-4 subtitle-1">
          Sales Tax Rate: 
          <input :value="getProduct.tax">%
        </div>
        
      </v-card-text>

  </v-card>
</template>


<script>
import {mapGetters} from 'vuex'
    // TODO: change all to editable fields to allow merchant to update
export default {
  props: ['barcode'],
  data() {
    return {
      name: "Enter Product Name",
      description: "Enter Product Description",
      price: 0,
      tax: 0,
    }
  },
  computed: {

    
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