const state = {
    listofcarts : [
        {   name: "cart1", 
            subtotal: cart.getCartSubTotal(),
            cart: cart.getCart()
        },  
        {   name: "cart2", 
            subtotal: cart.getCartSubTotal(),
            cart: cart.getCart()
        },
        {   name: "cart3", 
            subtotal: cart.getCartSubTotal(),
            cart: cart.getCart()
        }

    ],
    businessid: 1
};
const getters = {
getListOfCarts: (state) => state.listofcarts,
getBusinessID: (state) => state.businessid,
getCartName: (state) => state.name
};
const actions = {
async viewCartList(AHHHHHH){
    
}
};
const mutations = {
    addCart(state, cart) {
        state.listofcarts.push(cart)
    }
}
export default {
    state,
    getters,
    actions,
    mutations
}