<template>
  <div id="store">
    <Carts v-bind:carts="carts" v-on:verify="markPaid" />
  </div>
</template>

<script>
/*

        {
          barcodes:['bread'],
          quantities:[],
          businessID: 1,
          subtotal: 2,
          session:1112,
          tax: 2,
          total: 4,
          time: 2,
          cartHash:"",
          paid:true,
        },
        {
          barcodes: [],
          quantities: [],
          businessID: 2,
          subtotal: 2,
          session: 1112,
          tax: 2,
          total: 4,
          time: 2,
          cartHash: "",
          paid: false,
        },
        {
          barcodes: [],
          quantities: [],
          businessID: 3,
          subtotal: 2,
          session: 1113,
          tax: 2,
          total: 10,
          time: 2,
          cartHash: "",
          paid: false,
        },
  */
import {mapGetters, mapActions} from 'vuex';
import Carts from "@/components/merchant/Carts";
import Cookie from 'js-cookie'
export default {
  name: "store",
  components: {
    Carts,
  },
  mounted() {
    this.$emit("toggleHeader", "business")
    var authToken = Cookie.get('token')
    var businessID = this.getBusinessID
    console.log(businessID)
    console.log("AUTH",authToken)
    this.fetchCarts(businessID)
    .then((result) => { // no backend errors thrown
    this.$dbg_console_log(result)
    if(result.success==1) {
        //TODO: redirect to business dashboard
        this.$router.push('/store');
        this.$dbg_console_log("Successful login")
    } else {
        this.show_popup = true
        this.popupHeader =  "Login failure"
        this.popupText = result.message
    }
    }).catch(error => {
        this.show_popup = true
        this.popupHeader =  "Internal Server Error"
        this.popupText = "Something went wrong"
        this.$dbg_console_log(error)
    })
  },
  data() {
    return {
      carts: [
      ],
    };
  },
  computed: mapGetters(['getBusinessID']),
  methods: {
    ...mapActions(["fetchCarts"]),
    markPaid() {
      //this.$dbg_console_log(this.paid);
      this.$dbg_console_log("mark paid");
      this.cart.paid = !this.cart.paid;
    },
    addCart(newCart) {
      this.cart = [...this.carts, newCart];
    }
  },
};
</script>
