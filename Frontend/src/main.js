import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import store from './store'
import router from './router'
import debug from './mixins/debug'
import VScandit from 'vue-scandit';

Vue.config.productionTip = false

// https://vuejs.org/v2/guide/mixins.html
// Mixins are a flexible way to distribute reusable functionalities for Vue
// components. A mixin object can contain any component options. When a
// component uses a mixin, all options in the mixin will be “mixed” into 
// the component’s own options.
Vue.mixin(debug)
Vue.use(VScandit);

new Vue({
  store,
  vuetify,
  router,
  render: h => h(App)
}).$mount('#app')
