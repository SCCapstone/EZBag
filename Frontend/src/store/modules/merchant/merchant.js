///// Vuex Store Module Settings and Imports ////

// import debug function from mixin and destructure it
// (global mixins are only used by components, not vuex modules)
// import debug from '@/mixins/debug'
// const { methods } = debug
// const { $dbg_console_log } = methods

// import axios and set defaults
import axios from 'axios'


import Vue from 'vue'
import VueCookies from 'vue-cookies';
//import Cookie from 'js-cookie'
Vue.use(VueCookies);
/////////////////////////////////////////////////
const state = {

}

const getters = {

}

// https://vuex.vuejs.org/guide/actions.html#actions 
const actions = {

  
  async sha256(msg) {
    // encode as UTF-8
    const msgBuffer = new TextEncoder().encode(msg);                    
    // hash the message
    const hashBuffer = await crypto.subtle.digest('SHA-256', msgBuffer);
    // convert ArrayBuffer to Array
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    // convert bytes to hex string                  
    const hashHex = hashArray.map(b => ('00' + b.toString(16)).slice(-2)).join('');
    return hashHex;
  },

  async verifyToken(context, token) {
    var data = JSON.stringify({token: token})
    return new Promise((resolve, reject) => {
      axios.post("EZBagWebapp/webapi/merchant/token", data)
        .then(function (result) {
          resolve({
            status: result.data.status,
            message: result.data.message
          })
        }).catch(function (error) { // failed response from backend
          console.log(error)
          reject(error)
        })
      })
  },

  async fetchCarts(context, businessID) {
    var data = JSON.stringify({businessID:businessID})
    //var authToken = Cookie.get('token')
    /*
    {headers: {
        "Authorization":authToken,
        "Access-Control-Allow-Origin":"http://localhost:9000/",
      }}
    */
    return new Promise((resolve, reject) => {
      axios.post("EZBagWebapp/webapi/merchant/carts", data)
        .then(function (result) {
          if(result.data.status != "failure") {
            resolve({
              carts: result.data.carts,
              success: 1,
            })
          }
          else {
            resolve({
              success: 0,
              message: result.data.message,
            })
          }
            // product was not found by backend, so add only to known products
        }).catch(function (error) { // failed response from backend
          reject(error)
        })
      })
  },

  async loginUser(context, loginInfo) {
    return new Promise((resolve, reject) => {
      axios.post("EZBagWebapp/webapi/login",
        JSON.stringify(loginInfo))
        .then(function (result) {
          if(result.data.status != "failure") {
            resolve({
              success: 1,
              token: result.data.token,
              businessID: result.data.businessID
            })
          }
          else {
            resolve({
              success: 0,
              message: result.data.message,
            })
          }
            // product was not found by backend, so add only to known products
        }).catch(function (error) { // failed response from backend
          reject(error)
        })
      })
  },

  async registerUser(context, registrationInfo) {
    return new Promise((resolve, reject) => {
      axios.post("EZBagWebapp/webapi/register",
        JSON.stringify(registrationInfo))
        .then(function (result) {
          if(result.data.status != "failure") {
            resolve({
              success: 1
            })
          }
          else {
            resolve({
              success: 0,
              message: result.data.message,
            })
          }
            // product was not found by backend, so add only to known products
            
        }).catch(function (error) { // failed response from backend
          reject(error)
        })
      })
  },

  async lookupProduct(context, {barcode, businessID}) {
    return new Promise((resolve, reject) => {
        axios.post("EZBagWebapp/webapi/lookup",
        JSON.stringify({
            barcode: barcode,
            businessID: businessID
          }))
        .then(function (result) {
          if(result.data.status != "failure") {
            var product = {
              barcode: result.data.barcode,
              name:result.data.name,
              price: result.data.price,
              tax: Math.round((result.data.tax/result.data.price)*1000)/10,
              description:result.data.description,
              businessID:businessID,
              exists:true
            }
            resolve({
              product: product,
            })
          }
          else {
            resolve({
              product:null,
            })
          }
            // product was not found by backend, so add only to known products
        }).catch(function (error) { // failed response from backend
          reject(error)
        })
    })
  },

  async addProduct(context, {barcode, barcodeType, businessID, name, description, price, tax}) {
    return new Promise((resolve, reject) => {
      axios.post("EZBagWebapp/webapi/product",
      JSON.stringify({
          barcode: barcode,
          barcodeType: barcodeType, 
          businessID: businessID,
          name: name,
          description: description,
          price: price,
          tax: tax,
        }))
      .then(function (result) {
        if(result.data.status != "failure") {
          resolve({
            productAdded:true,
          })
        }
        else {
          resolve({
            productAdded:false,
          })
        }
      }).catch(function (error) { // failed response from backend
        reject(error)
      })
    })
  },

  async deleteProduct(context, {barcode, businessID}) {
    return new Promise((resolve, reject) => {
      axios.post("EZBagWebapp/webapi/delete",
      JSON.stringify({
          barcode: barcode,
          businessID: businessID,   
        }))
      .then(function (result) {
        if(result.data.status != "failure") {
          resolve({
            productDeleted:true,
          })
        }
        else {
          resolve({
            productDeleted:false,
          })
        }
      }).catch(function (error) { // failed response from backend
        reject(error)
      })
    })
  },

  async fetchCartsInterval(context, data) {
    data = JSON.stringify(data)
    //var authToken = Cookie.get('token')
    /*
    {headers: {
        "Authorization":authToken,
        "Access-Control-Allow-Origin":"http://localhost:9000/",
      }}
    */
    return new Promise((resolve, reject) => {
      axios.post("EZBagWebapp/webapi/merchant/sales", data)
        .then(function (result) {
          if(result.data.status != "failure") {
            resolve({
              carts: result.data.carts,
              success: 1,
            })
          }
          else {
            resolve({
              success: 0,
              message: result.data.message,
            })
          }
            // product was not found by backend, so add only to known products
        }).catch(function (error) { // failed response from backend
          reject(error)
        })
      })
  },

  // TODO: create get carts api call will token as Authorization parameters
}


// https://vuex.vuejs.org/guide/mutations.html#mutations-must-be-synchronous
// mutations are synchronous functions that modify client state
const mutations = {

}

export default {
  state,
  getters,
  actions,
  mutations
}

