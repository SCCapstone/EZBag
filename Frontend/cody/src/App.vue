<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center">
        <p>EZBag</p>

        <v-spacer></v-spacer>
      </div>
    </v-app-bar>
    <v-main>
      <Products />
    </v-main>
  </v-app>
</template>

<script>
import Products from './components/checkout/Products'

export default {
  name: 'App',

  components: {
    Products
  },

  data: () => ({
    //
  }),
};
</script>
