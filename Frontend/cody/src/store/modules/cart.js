//import axios from 'axios';
// this is a vuex state module, it is called by store/index.js 

//TODO: remove default products when no longer needed for debug
const state = {
  cart: [
    {
      barcode: "9780061241895",
      name: "Influence, The Psychology of Persuasion",
      price: "10.99",
      description: "National Best Seller. By Robert B. Cialdini, PH.D.",
      businessID: "1",
      quantity: 1,
    },
    {
      barcode:"abc",
      name:"chip",
      price:"5.99",
      description:"good ole chips",
      businessID:"1",
      quantity: 2,
    }
  ]
};

const getters = {
  getCart: (state) => state.cart
};


// https://vuex.vuejs.org/guide/actions.html#actions 
const actions = {
  //TODO: send event to backend
  async addProduct({ commit }, {barcode, name, price, description, businessID}) {
    commit('addProduct', {barcode:barcode, name:name, price:price,
      description:description, businessID:businessID, quantity:1})
  },

  // send product-remove-event to backend and call corresponding mutation
  async removeProduct({ commit }, barcode) {
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
    commit('changeProductQuantity', {barcode:barcode, amount:amount})

  }
};


// https://vuex.vuejs.org/guide/mutations.html#mutations-must-be-synchronous
// mutations are synchronous functions that modify client state and should only
// be called by the above actions 
const mutations = {
  // add product to cart
  //TODO: check if item exists before attempting to add product -
  //      increment quantity if already in cart
  addProduct:(state, product) => state.cart.push(product),

  // remove product from cart
  removeProduct:(state, barcode) => 
    state.cart = state.cart.filter(product => product.barcode !== barcode),

  // set quantity of product in cart to the provided amount
  //TODO: check if item exists before attempting to change quantity
  setProductQuantity:(state, {barcode, amount}) =>
    getProductFromCart(state, barcode).quantity = amount,
  
  // add the provided amount to the product in the cart
  //TODO: check if item exists before attempting to change quantity
  adjustProductQuantity:(state, {barcode, amount}) =>
    getProductFromCart(state, barcode).quantity += amount,
  };

// helper functions only to be called from within this module

// returns getters and setters for the given object
function getProductFromCart(state, barcode){
  const ret = state.cart.find(product => product.barcode == barcode);
  console.log('>>> Searching for product in cart by barcode:')
  console.log(ret);
  return ret;
}

export default {
  state,
  getters,
  actions,
  mutations
}