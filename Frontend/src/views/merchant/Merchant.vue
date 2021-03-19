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
export default {
  methods: {
    logout() {
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