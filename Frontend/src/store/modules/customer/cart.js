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
  knownProducts: [],
  productsInCart: [],
  businessID: 1,
  cartHash: null,
}

const getters = {
  getProductsInCart: (state) => state.productsInCart,
  getSubtotal: (state) => (Math.ceil(state.productsInCart.reduce((acc, val) => acc + val.price*val.quantity, 0)*100))/100,
  getTax: (state) => (Math.ceil(state.productsInCart.reduce((acc, val) => acc + val.tax*val.quantity, 0)*100))/100,
  getBusinessID: (state) => state.businessID,
  getCartHash: (state) => state.cartHash,
}


// https://vuex.vuejs.org/guide/actions.html#actions 
const actions = {

  async sendReceipt(context, receiptInfo) {
    return new Promise((resolve, reject) => {
      axios.post("EZBagWebapp/webapi/info",
        JSON.stringify(receiptInfo), {timeout: "2000"})
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

  async loginUser(context, loginInfo) {
    return new Promise((resolve, reject) => {
      axios.post("EZBagWebapp/webapi/login",
        JSON.stringify(loginInfo))
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

  // attempts to add product to cart
  async addProductToCart(context, {barcode, businessID}) {
    return new Promise((resolve, reject) => {
      var product = context.state.productsInCart.find(product => product.barcode == barcode)
      if(product === undefined) {
        // product is not in cart, so check if product is known by frontend
        product = context.state.knownProducts.find(product => product.barcode == barcode)
        if(product === undefined) {
          // product is not known by frontend, so query backend for product
          axios.post("EZBagWebapp/webapi/lookup",
            JSON.stringify({
              barcode: barcode,
              businessID: businessID
            }))
            .then(function (result) {
              // backend has succesfully responded (may or may not have found product),
              // so construct product based on response
              if(result.data.status != "failure") {
                // product was found by backend, so add to known products and cart
                context.commit('addToKnownProducts', {
                  barcode: result.data.barcode,
                  name:result.data.name,
                  price: result.data.price,
                  tax: result.data.tax,
                  description:result.data.description,
                  businessID:businessID,
                  exists:true
                })
                context.commit('addToCartFromKnownProducts', barcode)
                resolve({
                  productIsInCart:true,
                  productWasAlreadyInCart:false,
                  initialProductQuantity: 1
                })
              }
              else 
                // product was not found by backend, so add only to known products
                context.commit('addToKnownProducts', {
                  barcode:barcode,
                  exists:false
                })
                resolve({
                  productIsInCart:false,
                  productWasAlreadyInCart:false,
                  initialProductQuantity: -1
                })
            })
            .catch(function (error) { // failed response from backend
              reject(error)
            })
        }
        else {
          // product is known by frontend, but it is not in the cart
          if(product.exists == true) {
            // the product is known by the frontend to exist in the backend's DB, so add it to the cart
            context.commit('addToCartFromKnownProducts', barcode)
            resolve({
              productIsInCart:true,
              productWasAlreadyInCart:false,
              initialProductQuantity: 1
            })
          }
          else // product is known by the frontend to not exist in the backend's DB
            resolve({
              productIsInCart:false,
              productWasAlreadyInCart:false,
              initialProductQuantity: -1
            })
        }
      }
      else // product is already in the cart
        resolve({
          productIsInCart:true,
          productWasAlreadyInCart:true,
          initialProductQuantity: product.quantity
        })
    })
  },

  // attempts to checkout cart
  checkoutCart(context) {
    return new Promise((resolve, reject) => {
      if(context.state.productsInCart.length == 0) 
        resolve({ // cart is empty
          checkoutSuccesful:false,
          cartEmpty:true
        }) 
      else  // cart is not empty
        axios.post("EZBagWebapp/webapi/cart",
          JSON.stringify({
            barcodes: context.state.productsInCart.map(prod => prod.barcode),
            quantities: context.state.productsInCart.map(prod => prod.quantity),
            session: "SES" + Cookie.get('userToken'),
            businessID: context.state.businessID
          }))
          .then(function (result) { // backend responded
            if(result.data.status != "failure") { // Successfully submitted cart
              context.commit('setCartHash', result.data.hash)
              context.commit('emptyCart')
              resolve({
                checkoutSuccesful: true,
                cartEmpty:true
              }) 
            }
            else { // backend could not submit cart
              resolve({
                checkoutSuccesful: false,
                cartEmpty: false
              })
            }
          })
          .catch(function (error) { // failed response from backend
            reject(error)
          })
    })
  }
}


// https://vuex.vuejs.org/guide/mutations.html#mutations-must-be-synchronous
// mutations are synchronous functions that modify client state
const mutations = {
  
  addToKnownProducts(state, aProduct) {
    const debugString = 'Attempted to add product, '+aProduct.barcode+', to cart, but '
    if(state.knownProducts.find(product => product.barcode == aProduct.barcode) != undefined)
      $dbg_console_log(debugString+'it is already known.')
    else {
      state.knownProducts.push(aProduct)
    }
  },

  // searches known products for a product that is exists
  addToCartFromKnownProducts (state, barcode) {
    const debugString = 'Attempted to add product, '+barcode+', to cart, but '
    if(state.productsInCart.find(product => product.barcode == barcode) != undefined)
      $dbg_console_log(debugString+'it is already in the cart.')
    else {
      let product = state.knownProducts.find(product => product.barcode == barcode)
      if(product == undefined)
        $dbg_console_log(debugString+'it is not known by frontend')
      else // product is known by frontend
        if(product.exists == true)
          // product is known by backend, so add it to the cart
          state.productsInCart.push({
            barcode:product.barcode,
            name:product.name,
            price: product.price,
            tax: product.tax,
            quantity: 1,
            description:product.description
          })
        else // product is not known by backend
          $dbg_console_log(debugString+'it does not exist in backend\'s DB')
    }
  },
  
  // remove product from cart
  removeProductFromCart (state, barcode) {
    state.productsInCart = state.productsInCart.filter(product => product.barcode !== barcode)
  },

  // Alters the quantity of product whose barcode matches the supplied one.
  // Accepts a barcode and a second parameter - typeOrAmount, which can be
  // either a string describing an "INCREMENT" or "DECREMENT" operation to be
  // applied to the product's quantity, or it should be a number that will
  // overwrite the product's quantity.
  setProductQuantity (state, {barcode, typeOrAmount}) {
    const debugString = 'Attempted to change quantity of product, '+barcode+', but '
    const product = state.productsInCart.find(product => product.barcode == barcode)
    if(product == undefined)
      $dbg_console_log(debugString+'it is not in the cart')
    else
      switch(typeof(typeOrAmount)){
        case "number":
          product.quantity = typeOrAmount
          break
        case "string":
          switch(typeOrAmount){
            case "INCREMENT":
              product.quantity += 1
              break
            case "DECREMENT":
              product.quantity -= 1
              break
            default:
              $dbg_console_log(debugString+typeOrAmount+'is not a recognized change type')
          }
      }
  },

  setCartHash (state, cartHash) {
    state.cartHash = cartHash
  },

  emptyCart (state) {
    state.productsInCart = []
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}

