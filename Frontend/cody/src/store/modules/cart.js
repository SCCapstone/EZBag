//import axios from 'axios';

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

// actions send events to backend, then call their respective mutations for managing client's state 
const actions = {
  //TODO: write function to add product and send event to backend

  // send product-remove-event to backend and call corresponding mutation
  async deleteProduct({ commit }, barcode) {
    //TODO: send product removal event to backend
    commit('removeProduct', barcode)
  },

  // send product-change-event to backend and call corresponding mutation
  // change the quantity of a product by its barcode
  // if (changeType ==="set") then "amount" will become the new product.quantity
  // if (changeType ==="difference") then "amount" will be added to product.quantity 
  async changeProductQuantity({ commit }, {barcode, changeType, amount}) {
    //TODO: send product-quantity-change event to backend
    if(changeType === 'set'){
      commit('setProductQuantity', {barcode:barcode, amount:amount})
    }
    else if (changeType === 'difference'){
      const newQuantity = state.cart.find(product => product.barcode == barcode).quantity + amount
      commit('setProductQuantity', {barcode:barcode, amount:newQuantity})
    }
    else{
      console.log('changeProductQuantity Error: "changeType" takes on only values of "set" and "difference"')
    }
  }
};

// mutations modify client state and should only be called by actions 
const mutations = {
  //addProduct: (state, product) => (state.products += product)
  //newProduct:

  // remove product from cart
  removeProduct:(state, barcode) => 
    state.cart = state.cart.filter(product => product.barcode !== barcode),

  // change quantity of product in cart
  setProductQuantity(state, {barcode, amount}) {
    state.cart.find(product => product.barcode == barcode).quantity = amount
    }
  };

export default {
  state,
  getters,
  actions,
  mutations
}