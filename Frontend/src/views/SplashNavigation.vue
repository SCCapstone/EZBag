<template>
  <div>
    <v-app-bar height="60" color="primary" dark>
      <v-app-bar-nav-icon
        @click="drawer = true">
      </v-app-bar-nav-icon>
      <v-toolbar-title
        class="text-no-wrap"
        style='text-overflow:visible; overflow:visible;'>
      </v-toolbar-title>
      <div
        class="app2 text-subtitle-2">
      </div>
      <v-col class="text-right">
      <div class="icon" id="app"
        >
        <img id="app" width="38" height="40" :src="require('@/assets/EZ_Drop.svg')"/>

      </div>
      <v-divider>
      </v-divider>
      </v-col>
      <div class="iconText">
      EZBag
      </div>

    </v-app-bar>
    <v-app-bar height="45" color="gray" dark>
    <div class="center">
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

<style scoped>
  .center {
    margin: auto;
    font-weight: 300;
    

  }
  .iconText{
      font-size: 18px;
      font-weight: bolder;
  }
  .icon{
    position: relative;
    top: 2px
  }
</style>
