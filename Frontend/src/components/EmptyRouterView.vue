<template>
  <v-main style='z-index:0'> <!-- fixes z-indexing for merchant scanner -->
    <router-view name="navigation"/>
    <router-view/>
  </v-main>
</template>

<script>
// Neccesary because of technical limitations with view. For more info, see:
// https://dev.to/berniwittmann/my-approach-on-vue-router-architecture-and-nested-routes-2kmo
export default {

}
</script>