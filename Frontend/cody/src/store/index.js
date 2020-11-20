// entry point for vuex

import Vue from 'vue';
import Vuex from 'vuex';
import VuexPersist from 'vuex-persist';
import cart from './modules/cart';


// Load Vuex
Vue.use(Vuex);

// https://www.digitalocean.com/community/tutorials/vuejs-vuex-persist-state
// TODO: consider using the reducer to only save state that we want to persist (currently saves everything)
const vuexLocalStorage = new VuexPersist({
  key: 'vuex', // The key to store the state on in the storage provider.
  storage: window.localStorage, // or window.sessionStorage or localForage
  // Function that passes the state and returns the state with only the objects you want to store.
  // reducer: state => state,
  // Function that passes a mutation and lets you decide if it should update the state in localStorage.
  // filter: mutation => (true)
})

// Create store 
export default new Vuex.Store({
  modules: {
    cart
  },
  plugins: [vuexLocalStorage.plugin]
});
