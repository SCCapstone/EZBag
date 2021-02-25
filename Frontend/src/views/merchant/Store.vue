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
        {"carts":
          [{"barcodes":
            ["12345678","12345678"],
            "quantities":[3,2],
            "businessID":"cad1ab052ffff19ff3f595c569f7a37f826921d07c4262946d81ef04ec72d727",
            "subtotal":34.95,
            "session":"2",
            "tax":23.08,
            "total":58.03,
            "time":{"$numberLong":"1614190765106"},
            "cartHash":"meeauCHFaWFP6lCPEsyM",
            "verified":false
          },
          {"barcodes":["12345678","12345678"],
            "quantities":[3,2],
            "businessID":"cad1ab052ffff19ff3f595c569f7a37f826921d07c4262946d81ef04ec72d727",
            "subtotal":34.95,
            "session":"2",
            "tax":23.08,
            "total":58.03,
            "time":{"$numberLong":"1614190804970"},
            "cartHash":"wLtqzC34bfFaxErqnVW2",
            "verified":false},
            {"barcodes":["12345678","12345678"],
            "quantities":[3,2],
            "businessID":"cad1ab052ffff19ff3f595c569f7a37f826921d07c4262946d81ef04ec72d727",
            "subtotal":34.95,
            "session":"2",
            "tax":23.08,
            "total":58.03,
            "time":{"$numberLong":"1614190805635"},
            "cartHash":"KpaE791tFEiK2zC2P2Fx",
            "verified":false
          }
          ],"status":"success"}
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
        this.carts = result.carts
    } else {
        console.log("Failed")
        this.carts = this.debugCarts
        //this.show_popup = true
        //this.popupHeader =  "Login failure"
        //this.popupText = result.message
    }
    }).catch(error => {
        this.show_popup = true
        this.popupHeader =  "Internal Server Error"
        this.popupText = "Something went wrong"
        this.carts = this.debugCarts   
        //this.$dbg_console_log(error)
        if(error=="dumbassFuckingViewWarn"){
          console.log("Vue warnings are stupid")
        }
    })
  },
  data() {
    return {
      carts: [
      ],
      debugCarts:
          [{"barcodes":
            ["12345678","12345678"],
            "names":["Tooth brush", "Extra gum"],
            "quantities":[3,2],
            "businessID":"cad1ab052ffff19ff3f595c569f7a37f826921d07c4262946d81ef04ec72d727",
            "subtotal":34.95,
            "session":"2",
            "tax":23.08,
            "total":58.03,
            "time":{"$numberLong":"1614190765106"},
            "cartHash":"meeauCHFaWFP6lCPEsyM",
            "verified":false,
            "expanded":false
          },
          {"barcodes":["12345678","12345678"],
            "names":["Tooth brush", "Extra gum"],
            "quantities":[3,2],
            "businessID":"cad1ab052ffff19ff3f595c569f7a37f826921d07c4262946d81ef04ec72d727",
            "subtotal":34.95,
            "session":"2",
            "tax":23.08,
            "total":58.03,
            "time":{"$numberLong":"1614190804970"},
            "cartHash":"wLtqzC34bfFaxErqnVW2",
            "verified":false,
            "expanded":false
          },
          {"barcodes":["12345678","12345678"],
            "names":["Tooth brush", "Extra gum"],
            "quantities":[3,2],
            "businessID":"cad1ab052ffff19ff3f595c569f7a37f826921d07c4262946d81ef04ec72d727",
            "subtotal":34.95,
            "session":"2",
            "tax":23.08,
            "total":58.03,
            "time":{"$numberLong":"1614190805635"},
            "cartHash":"KpaE791tFEiK2zC2P2Fx",
            "verified":false,
            "expanded":false
          }]
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
