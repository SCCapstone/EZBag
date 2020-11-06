// entry point for vuex

import Vuex from 'vuex';
import Vue from 'vue';
import products from './modules/products';


// Load Vuex
Vue.use(Vuex);

// Create store 
export default new Vuex.Store({
  modules: {
    products
  }  
});
