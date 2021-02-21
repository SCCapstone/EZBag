///// Vuex Store Module Settings and Imports ////

// import debug function from mixin and destructure it
// (global mixins are only used by components, not vuex modules)
import debug from '@/mixins/debug'
const { methods } = debug
const { $dbg_console_log } = methods

// import axios and set defaults
import axios from 'axios'
axios.defaults.baseURL = process.env.VUE_APP_ROOT_API
axios.defaults.timeout = 2000
// if in development, then all axios requests and responses will be logged
if(process.env.VUE_APP_SHOW_DEBUG=='true') {
  axios.interceptors.request.use(request => {
    $dbg_console_log('Starting Request', request)
    return request
  })
  axios.interceptors.response.use(response => {
    $dbg_console_log('Response', response)
    return response
  })
}

import Cookie from 'js-cookie'
/////////////////////////////////////////////////
const state = {
  businessID2: null,
}

const getters = {
  getBusinessID2: (state) => state.businessID,
}

// https://vuex.vuejs.org/guide/actions.html#actions 
const actions = {

  async loginUser(context, loginInfo) {
    return new Promise((resolve, reject) => {
      axios.post("EZBagWebapp/webapi/login",
        JSON.stringify(loginInfo))
        .then(function (result) {
          if(result.data.status != "failure") {
            Cookie.set("token", result.data.token, {secure: true, expires: 99983090}) 
            context.commit("setBusinessID", result.data.businessID)
            resolve({
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

  // TODO: create get carts api call will token as Authorization parameters
}


// https://vuex.vuejs.org/guide/mutations.html#mutations-must-be-synchronous
// mutations are synchronous functions that modify client state
const mutations = {
  // set businessID method
  setBusinessID (state, businessID) {
    state.businessID = businessID;
  },

}

export default {
  state,
  getters,
  actions,
  mutations
}

