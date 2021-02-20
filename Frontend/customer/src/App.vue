<template>
  <v-app>
    <v-app-bar app elevate-on-scroll color="primary" dark>
      <v-tabs fixed-tabs>
        <v-tab to="/">Home</v-tab>
        <v-tab to="/cart">Cart</v-tab>
      </v-tabs>
    </v-app-bar>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

export default {

}
</script>
