//import axios from 'axios';

//TODO: remove default products when no longer needed for debug
const state = {
  products: [
    {
      barcode: "9780061241895",
      name: "Influence, The Psychology of Persuasion",
      price: "10.99",
      description: "National Best Seller. By Robert B. Cialdini, PH.D.",
      businessID: "1",
    },
    {
      barcode:"abc",
      name:"chip",
      price:"5.99",
      description:"good ole chips",
      businessID:"1"
    }
  ]
};

const getters = {
  allProducts: (state) => state.products
};

// actions send events to backend, then call their respective mutations for managing client's state 
const actions = {
  //TODO: write function to add product and send event to backend

  // send product-remove-event to backend  
  async deleteProduct({ commit }, barcode) {
    //TODO: send product removal event to backend
    commit('removeProduct', barcode)
  }
};

// mutations modify client state and should only be called by actions 
const mutations = {
  //addProduct: (state, product) => (state.products += product)
  //newProduct:
  // remove product from cart
  removeProduct:(state, barcode) => 
    state.products = state.products.filter(product => product.barcode !== barcode)

};

export default {
  state,
  getters,
  actions,
  mutations
}