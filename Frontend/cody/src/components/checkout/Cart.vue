<template>
  <div>
    <v-container class="products">
      <v-row
        v-for="product in getCart"
        v-bind:key="product.barcode"
      >
        <v-col>
          <v-card>
            <v-card-title>{{ product.name }}</v-card-title>
            <v-card-actions>
              <span class="subheading ml-2"> ${{ product.price }}</span>
              <v-spacer></v-spacer>
              <!-- Remove product button - conditionally rendered-->
              <v-btn 
                @click="removeProduct({barcode:product.barcode})"
                v-show="product.quantity === 1"
                icon
              >
              <!-- Decrement product quantity - conditionally rendered on product.quantity-->
                <v-icon>mdi-delete</v-icon>
              </v-btn>
              <v-btn 
                @click="adjustProductQuantity({barcode:product.barcode,amount:-1})"
                v-show="product.quantity !== 1" 
                icon
              >
                <v-icon>mdi-minus</v-icon>
              </v-btn>
              <!-- Item quantity -->
              <!-- TODO: make this a button, which can be clicked to edit the quantity directly-->
              <span class="subheading mr-2 ml-2"> {{ product.quantity }}</span>
              <!-- Increment product quantity -->
              <v-btn @click="adjustProductQuantity({barcode:product.barcode,amount:1})" icon>
                <v-icon>mdi-plus</v-icon>
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
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

<style scoped>

/*
  Fix for word-wrapping in title. To be fixed in Vuetify 3.
  https://github.com/vuetifyjs/vuetify/issues/9130#issuecomment-542534966
*/
.v-card__text, .v-card__title {
  word-break: normal;
}

</style>