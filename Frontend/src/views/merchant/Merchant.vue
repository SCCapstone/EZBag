<template>
  <div>
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
  </div>
</template>

<script>
import {mapActions} from 'vuex';
import Vue from 'vue'
import VueCookies from 'vue-cookies';
Vue.use(VueCookies);
export default {
  created() {
    console.log("Merchant Navigation Created")
    if (this.$cookies.get("token") !== null) {
      this.verifyToken(this.$cookies.get("token")).then( (result) => {
        if (result.status=="success") {
          this.$dbg_console_log("verified token!")
        } else {
          this.$dbg_console_log("Token is not valid, redirecting to login")
          this.$router.push("/login")
          this.$cookies.remove("token")
        }
      }).catch(error => {    
          this.$dbg_console_log(error)
          this.$router.push("/login")
          this.$cookies.remove("token")
      })
    } else {
      this.$dbg_console_log("Cookie token is null, redirecting to login")
      this.$router.push("/login")
    }
    
  },
  methods: {
    ...mapActions(["verifyToken"]),
    logout() {
      this.$cookies.remove("token")
      this.$router.push('/login');
    }
  },
}
</script>

<style>
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