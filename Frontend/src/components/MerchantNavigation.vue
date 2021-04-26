<template>
  <v-container class="mx-auto overflow-hidden" style="z-index:100">
    <v-navigation-drawer
      app
      v-model="drawer"
      temporary
      hide-overlay>
      <v-list
        nav
        dense>
        <v-list-item-group>
          <v-list-item to="store">
            <v-list-item-icon>
              <v-icon>mdi-cart</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Cart Verification</v-list-item-title>
          </v-list-item>

          <v-list-item to="statistics">
            <v-list-item-icon>
              <v-icon>mdi-finance</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Analytics</v-list-item-title>
          </v-list-item>

          <v-list-item to="products">
            <v-list-item-icon>
              <v-icon>mdi-scan-helper</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Scanner</v-list-item-title>
          </v-list-item>

          <v-list-item v-on:click="customer">
            <v-list-item-icon>
              <v-icon>mdi-store</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Customer View</v-list-item-title>
          </v-list-item>

          <v-list-item v-on:click="logout">
            <v-list-item-icon>
              <v-icon>mdi-logout</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Logout</v-list-item-title>
          </v-list-item>

        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar color="primary" dark>
      <v-app-bar-nav-icon
        @click="drawer = true">
      </v-app-bar-nav-icon>
      <v-spacer></v-spacer>
      <img width="35px" height="35" src="@/assets/EZ_Drop.svg">
      <div class="text-h6 font-weight-bold mt-2 ml-2">EZBag</div>
    </v-app-bar>
  </v-container>
</template>

<script>
import Vue from 'vue'
import {mapMutations} from "vuex"
import VueCookies from 'vue-cookies';
Vue.use(VueCookies);
export default {
  data: () => ({
      drawer: false,
      // group: null,
    }),
  methods: {
    ...mapMutations(["setBusinessID"]),
    push(link) {
      if (link === 'github') {
        window.location.href = "https://github.com/SCCapstone/EZBag";
      } else {
        console.log(this.$router.currentRoute.path)
        if (this.$router.currentRoute.path !== link) {
          this.$router.push(link)
        }
        
      }
    },
    customer() {
      var businessID = this.$route.params.id
      this.$router.push('/customer/' +  businessID + '/scan')
    },
    logout() {
      this.setBusinessID(null)
      this.$cookies.remove("token")
      this.$router.push('/login')
    }
  },
}
</script>