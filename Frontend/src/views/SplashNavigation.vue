<template>
    <v-card
        class="mx-auto overflow-hidden"
    >
    <v-app-bar
      color="primary"
      dark
    >
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

      <v-toolbar-title>Welcome to EZBag</v-toolbar-title>
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      absolute
      temporary
    >
      <v-list
        nav
        dense
      >
        <v-list-item-group
        >
          <v-list-item v-on:click="push('/')">
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Home</v-list-item-title>
          </v-list-item>

          <v-list-item v-on:click="push('/login')">
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Login / Register</v-list-item-title>
          </v-list-item>

          <v-list-item v-on:click="push('/about')">
            <v-list-item-icon>
              <v-icon>mdi-information-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>About</v-list-item-title>
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

  </v-card>
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

<style scoped>

</style>
