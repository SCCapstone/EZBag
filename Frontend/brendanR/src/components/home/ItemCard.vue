<template>
  <div id="itemCard">
    
  </div>
</template>

<script>
// https://vuex.vuejs.org/guide/actions.html#dispatching-actions-in-components
import { mapGetters, mapActions } from 'vuex';

export default {
  name:"ItemCard",
  methods: {
    ...mapActions([])
  },
  computed: mapGetters([])
}
</script>

<style scoped>

/*
  Fix for word-wrapping in title. To be fixed in Vuetify 3.
  https://github.com/vuetifyjs/vuetify/issues/9130#issuecomment-542534966
*/
#itemCard
{
  position: absolute;
  width: 100%;
  height: 70%;
  bottom: 0px;
  background: rgb(220, 220, 227);
}

.v-card__text, .v-card__title {
  word-break: normal;
}

</style>