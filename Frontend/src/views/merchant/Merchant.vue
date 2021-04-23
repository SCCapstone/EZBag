<template>
  <div>
    <!--
    <v-app-bar app elevate-on-scroll color="primary" dark>
        <v-app-bar class="tabs" app elevate-on-scroll color="primary" dark>
          <v-tabs fixed-tabs>
              <v-tab to="store">Verification</v-tab>
              <v-tab to="statistics">Sales</v-tab>
              <v-tab to="products">Products</v-tab>
          </v-tabs>
        </v-app-bar>
        <v-tab v-on:click.stop @click="logout()" class="logout"><v-icon>mdi-logout</v-icon></v-tab>
    </v-app-bar>
    <router-view/>
    -->
    <v-app-bar app elevate-on-scroll color="primary" dark>
      <v-app-bar-nav-icon
        @click="drawer = true">
      </v-app-bar-nav-icon>
      <v-toolbar-title
        class="text-no-wrap"
        style='text-overflow:visible; overflow:visible;'>
        EZBag
      </v-toolbar-title>
      <v-divider class='mx-4' vertical inset></v-divider>
      <div
        class="text-subtitle-2">
      </div>
    </v-app-bar>
    <v-navigation-drawer
      class="top"
      v-model="drawer"
      absolute
      temporary>
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

          <v-list-item v-on:click="logout">
            <v-list-item-icon>
              <v-icon>mdi-logout</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Logout</v-list-item-title>
          </v-list-item>

        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
    <router-view/>
  </div>
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
    logout() {
      this.setBusinessID(null)
      this.$cookies.remove("token")
      this.$router.push('/login')
    }
  },
}
</script>

<style>
  .top{
    z-index: 100;
  }
  .tabs{
    width: calc(100% - 50px);
  }
  .logout
  {
    position: absolute;
    top: 0px;
    width: 50px;
    height: 100%;
    right: -22px;
  }
</style>