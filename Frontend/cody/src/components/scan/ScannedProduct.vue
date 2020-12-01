<template>
  <v-card>
    <v-card-title>{{ name }}</v-card-title>
    <v-card-actions>
      <span class="subheading ml-2"> ${{ price }}</span>
      <v-spacer></v-spacer>
      <!-- Remove product button - conditionally rendered-->
      <v-btn 
        @click="removeProduct({barcode})"
        v-show="quantity === 1"
        icon
      >
      <!-- Decrement product quantity - conditionally rendered on product.quantity-->
        <v-icon>mdi-delete</v-icon>
      </v-btn>
      <v-btn 
        @click="adjustProductQuantity({barcode:barcode,amount:-1})"
        v-show="quantity !== 1" 
        icon
      >
        <v-icon>mdi-minus</v-icon>
      </v-btn>
      <!-- Item quantity -->
      <!-- TODO: make this a button, which can be clicked to edit the quantity directly-->
      <span class="subheading mr-2 ml-2"> {{ quantity }}</span>
      <!-- Increment product quantity -->
      <v-btn @click="adjustProductQuantity({barcode:barcode,amount:1})" icon>
        <v-icon>mdi-plus</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  props: ['barcode',
          'name',
          'price',
          'description',
          'businessID',
          'quantity'],
  methods: {
  ...mapActions([ "removeProduct",
                  "setProductQuantity",
                  "adjustProductQuantity"
                ])
  }
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