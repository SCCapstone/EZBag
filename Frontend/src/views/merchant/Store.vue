<template>
  <div id="store">
    <Carts v-bind:carts="carts" v-on:verify="markPaid" />
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import Carts from "@/components/merchant/Carts";
export default {
  name: "store",
  components: {
    Carts,
  },
  mounted() {
    this.fetchCarts(this.$route.params.id)
      .then((result) => { // no backend errors thrown
      this.$dbg_console_log(result)
        if(result.success==1) {
            //TODO: redirect to business dashboard
            this.$dbg_console_log("Successful fetch of carts")
            
            var count = result.carts.length;
            for(var i=0; i<count; i++)
            {
              result.carts[i]["expanded"]=false
              var milliTime = Number(result.carts[i]["time"]["$numberLong"])
              result.carts[i]["dt"]= new Date(milliTime).toLocaleString();
            }
            this.carts = result.carts
            //this.carts = this.debugCarts   
        } else {
            this.$dbg_console_log("Backend Error: Failed to fetch carts")
            //this.carts = this.debugCarts
            //this.show_popup = true
            //this.popupHeader =  "Login failure"
            //this.popupText = result.message
        }
      }).catch(error => {
          this.show_popup = true
          this.popupHeader =  "Internal Server Error"
          this.popupText = "Something went wrong"
          this.carts = this.debugCarts   
          this.$dbg_console_log(error)
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
