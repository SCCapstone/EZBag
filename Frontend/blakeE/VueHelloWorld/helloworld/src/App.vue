<template>
  <div id="app">
    <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
    <!-- <HelloWorld msg="Welcome to Your Vue.js App"/> -->
    <Scanner total="0.0"/>
  </div>
</template>

<script>
// import HelloWorld from './components/HelloWorld.vue'
import Scanner from './components/Scanner.vue'

export default {
  name: 'App',
  components: {
    Scanner
  }
}
</script>

<style>
#app {
  height: 100%;
  width: 100%;
}
</style>
