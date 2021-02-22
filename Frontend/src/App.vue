<template>
  <v-app>

      <div v-if="show_customer_header">
        <v-app-bar app elevate-on-scroll color="primary" dark>
          <v-tabs fixed-tabs>
            <v-tab to="/">Home</v-tab>
            <v-tab to="/cart">Cart</v-tab>
          </v-tabs>
        </v-app-bar>
      </div>

      <div v-if="show_business_header">
        <v-app-bar app elevate-on-scroll color="primary" dark>
          <v-tabs fixed-tabs>
            <v-tab to="/store">Cart Verification</v-tab>
            <v-tab to="/statistics">Sales Dashboard</v-tab>
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
    show_customer_header: false,
    show_business_header: false
  }),
  methods: {
    toggleHeader(value)
    {
      // show custmer nav
      if (value == "customer") {
        this.show_customer_header = true
        this.show_business_header = false
      } else if (value == "business") {
        // show bix nav
        this.show_customer_header = false
        this.show_business_header = true
      } else {
        // display no navigation bar
        this.show_customer_header = false
        this.show_business_header = false
      }
    },
  }

}
</script>
