// this is a vuex state module, it is called by store/index.js 

//TODO: remove default products when no longer needed for debug
const state = {
  cart: [

  ],
  businessID: 1,
  cartHash: null,
};

const getters = {
  getCart: (state) => state.cart,
  getCartSubtotal: (state) => (Math.ceil(state.cart.reduce((acc, val) => acc + val.price*val.quantity, 0)*100))/100,
  getCartTax: (state) => (Math.ceil(state.cart.reduce((acc, val) => acc + val.tax*val.quantity, 0)*100))/100,
  getCartBusinessID: (state) => state.businessID,
  getSessionID: (state) => state.sessionID,
  getCartHash: (state) => state.cartHash,
};


// https://vuex.vuejs.org/guide/actions.html#actions 
const actions = {
  //TODO: send event to backend
  async addProduct({ commit }, {barcode, name, tax, price, description, businessID}) {
    if(state.cart.find(product => product.barcode == barcode) === undefined) {
      commit('addProduct', {barcode:barcode, name:name, price:price, tax:tax,
        description:description, businessID:businessID, quantity:1})
    }
  },

  // send product-remove-event to backend and call corresponding mutation
  async removeProduct({ commit }, {barcode}) {
    //TODO: send product removal event to backend
    commit('removeProduct', barcode)
  },

  // send product-quantity-change-event to backend and call corresponding mutation
  // to set the product quantity to the given amount
  async setProductQuantity({ commit }, {barcode, amount}) {
    //TODO: send product-quantity-change event to backend
    commit('setProductQuantity', {barcode:barcode, amount:amount})
  },

  // send product-qunatity-adjust-event to backend and call corresponding mutation
  // to add the given amount to the product quantity
  async adjustProductQuantity({ commit }, {barcode, amount}) {
    //TODO: send product-qunatity-adjust-event to backend
    commit('adjustProductQuantity', {barcode:barcode, amount:amount})
  },



  setCartHash({commit}, {cartHash}) {
    commit('setCartHash', cartHash)
  },

  emptyCart({commit}) {
    commit('emptyCart', )
  },

};


// https://vuex.vuejs.org/guide/mutations.html#mutations-must-be-synchronous
// mutations are synchronous functions that modify client state and should only
// be called by the above actions 
const mutations = {
  // add product to cart
  //TODO: check if item exists before attempting to add product -
  //      increment quantity if already in cart
  addProduct (state, product) {
    state.cart.push(product)
  },

  // remove product from cart
  removeProduct (state, barcode) {
    state.cart = state.cart.filter(product => product.barcode !== barcode)
  },

  // set quantity of product in cart to the provided amount
  //TODO: check if item exists before attempting to change quantity
  setProductQuantity (state, {barcode, amount}) {
    state.cart.find(product => product.barcode == barcode).quantity = amount
  },
  
  // add the provided amount to the product in the cart
  //TODO: check if item exists before attempting to change quantity
  adjustProductQuantity (state, {barcode, amount}) {
    state.cart.find(product => product.barcode == barcode).quantity += amount
  },

  setCartHash (state, cartHash) {
    state.cartHash = cartHash
  },

  emptyCart (state) {
    state.cart = []
  }
};

export default {
  state,
  getters,
  actions,
  mutations
}

