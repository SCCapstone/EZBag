// entry point for vuex

import Vuex from 'vuex';
import Vue from 'vue';
import cart from './modules/cart';


// Load Vuex
Vue.use(Vuex);

// Create store 
export default new Vuex.Store({
  modules: {
    cart
  }  
});
