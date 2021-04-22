<template>
  <div>
    <v-app-bar color="primary" dark>
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
        Enterprise Solutions for the Small-Box Store
      </div>
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      absolute
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
    <router-view/>
  </div>
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
