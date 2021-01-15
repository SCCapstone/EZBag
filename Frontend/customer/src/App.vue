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
import { mapGetters, mapActions} from 'vuex';

export default {
  computed: mapGetters(['getSessionID']),
  methods: mapActions(['generateSessionID']),
  created () {
    if (this.getSessionID === null)
      this.generateSessionID()
  },
}
</script>
