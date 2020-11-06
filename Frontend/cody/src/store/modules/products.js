import axios from 'axios';

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

const actions = {
  // remember that backend will be stateless. User will maintain state of cart until submitting to backend.
  // there is no reason to have a function for fetching products 
  async fetchProducts({ commit }) {
    //const response = await axios.get('https://')
  }
};

const mutations = {};

export default {
  state,
  getters,
  actions,
  mutations
}