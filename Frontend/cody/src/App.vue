<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center">
        <p>EZBag</p>

        <v-spacer></v-spacer>
      </div>
    </v-app-bar>
    <v-main>
      <Cart />
    </v-main>
  </v-app>
</template>

<script>
import Cart from './components/checkout/Cart'

export default {
  name: 'App',

  components: {
    Cart
  },

  data: () => ({
    //
  }),
};
</script>
