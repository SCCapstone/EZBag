<template>
  <div>
    <v-app-bar app elevate-on-scroll color="primary" dark>
      <v-tabs fixed-tabs>
          <v-tab to="store">Verification</v-tab>
          <v-tab to="statistics">Sales</v-tab>
      </v-tabs>
    </v-app-bar>
    <router-view/>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>