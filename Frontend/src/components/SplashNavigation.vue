<template>
  <v-container class="pa-0 overflow-hidden">
    <v-navigation-drawer
      app
      v-model="drawer"
      hide-overlay
      temporary>
      <v-list
        nav
        dense>
        <v-list-item-group>
          <v-list-item v-on:click="push('/')">
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Home</v-list-item-title>
          </v-list-item>

          <v-list-item v-on:click="push('/login')">
            <v-list-item-icon>
              <v-icon>mdi-login</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Login</v-list-item-title>
          </v-list-item>

          <v-list-item v-on:click="push('/register')">
            <v-list-item-icon>
              <v-icon>mdi-account-plus</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Register</v-list-item-title>
          </v-list-item>

          <v-list-item v-on:click="push('/about')">
            <v-list-item-icon>
              <v-icon>mdi-account-group</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Our Team</v-list-item-title>
          </v-list-item>

          <v-list-item v-on:click="push('github')">
            <v-list-item-icon>
              <v-icon>mdi-xml</v-icon>
            </v-list-item-icon>
            <v-list-item-title>GitHub</v-list-item-title>
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
    <v-card 
      class="text-center rounded-0 py-1"
      height
      color="secondary" dark>
      <div class="font-weight-light">
        Enterprise Solutions for the Small-Box Store
      </div>
    </v-card>
  </v-container>
</template>

<script>
// import Splash from '../views/Splash.vue';
export default {
    name: 'SplashNavigation',
    components: {
        
    },
    data: () => ({
      drawer: false,
      // group: null,
    }),
    methods: {
      push(link) {
        if (link === 'github') {
          window.location.href = "https://github.com/SCCapstone/EZBag";
        } else {
          console.log(this.$router.currentRoute.path)
          if (this.$router.currentRoute.path !== link) {
            this.$router.push(link)
          }
          
        }
      }
    }
};
</script>