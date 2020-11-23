<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <v-tabs fixed-tabs>
        <v-tab to="/">Home</v-tab>
        <v-tab to="/checkout">Checkout</v-tab>
        <v-tab to="/receipt">Receipt</v-tab>
      </v-tabs>
    </v-app-bar>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>