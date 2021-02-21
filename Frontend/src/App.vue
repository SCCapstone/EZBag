<template>
  <v-app>
      <div v-if="doShow()">
      <v-app-bar app elevate-on-scroll color="primary" dark>
        <v-tabs fixed-tabs>
          <v-tab to="/">Home</v-tab>
          <v-tab to="/cart">Cart</v-tab>
        </v-tabs>
      </v-app-bar>
      </div>
      <v-main>
        <router-view v-on:toggleHeader="toggleHeader"/>
      </v-main>
  </v-app>
</template>

<script>

export default {

  data: () => ({
    show_customer_header: false
  }),
  methods: {
    toggleHeader(value)
    {
      this.show_customer_header = value
    },
    doShow(){
      return this.show_customer_header
    }
  }

}
</script>
