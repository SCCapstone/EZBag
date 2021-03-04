// entry point for vuex

import Vue from 'vue';
import Vuex from 'vuex';
import VuexPersist from 'vuex-persist';
import cart from '@/store/modules/customer/cart';
import Cookie from 'js-cookie';
import merchant from '@/store/modules/merchant/merchant'

// Load Vuex
Vue.use(Vuex);

// TODO: remove this and get from login
// Setting cookies tut: https://sandulat.com/safely-persisting-vuex-store-in-local-storage/
const tokenCookieName = 'userToken'
// uuid gen courtesy of https://stackoverflow.com/questions/105034/how-to-create-a-guid-uuid
const token = Cookie.get(tokenCookieName) || ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
  (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16))
// about cookie expiration https://stackoverflow.com/questions/532635/javascript-cookie-with-no-expiration-date
Cookie.set(tokenCookieName, token, {secure: true, expires: 99983090})  // generate a new session ID 

// https://www.digitalocean.com/community/tutorials/vuejs-vuex-persist-state
// TODO: consider using the reducer to only save state that we want to persist (currently saves everything)
const vuexLocalStorage = new VuexPersist({
  key: 'vuex', // The key to store the state on in the storage provider.
  storage: window.localStorage, // or window.sessionStorage or localForage
  // Function that passes the state and returns the state with only the objects you want to store.
  reducer: state => ({
    cart: {
      productsInCart: state.cart.productsInCart
    },
    merchant: state.merchant
  }),
  // Function that passes a mutation and lets you decide if it should update the state in localStorage.
  // filter: mutation => (true)
})

// Create store 
export default new Vuex.Store({
  modules: {
    cart,
    merchant
  },
  plugins: [vuexLocalStorage.plugin]
});
