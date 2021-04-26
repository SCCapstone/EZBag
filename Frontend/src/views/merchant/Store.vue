<template>
  <Carts v-bind:carts="carts" v-on:verify="markPaid" />
</template>

<script>
import { mapActions } from 'vuex'
import Carts from "@/components/merchant/Carts";
export default {
  name: "store",
  components: {
    Carts,
  },
  data() {
    return {
      carts: [],
      timer: null
    };
  },
  methods: {
    ...mapActions(["fetchCarts"]),
    fetchNewCarts() {
      this.fetchCarts(this.$route.params.id)
      .then((result) => { // no backend errors thrown
      this.$dbg_console_log(result)
        if(result.success==1) {
            //TODO: redirect to business dashboard
            this.$dbg_console_log("Successful fetch of carts")
            
            if(result.carts.length <= this.carts.length)
              return

            var count = result.carts.length;
            for(var i=0; i<count; i++)
            {
              result.carts[i]["expanded"]=false
              var milliTime = Number(result.carts[i]["time"]["$numberLong"])
              result.carts[i]["dt"]= new Date(milliTime).toLocaleTimeString().replace(/:\d+ /, ' ');
            }
            var newCarts = []
            for(i=0; i<count; i++)
            {
              newCarts[i] = result.carts[count-i-1]
              if(i > 0 && i < this.carts.length && this.carts[i-1].expanded){
                newCarts[i]["expanded"]=true
              }
            }
            this.carts = newCarts
            //this.carts = this.debugCarts   
        } else {
            this.$dbg_console_log("Backend Error: Failed to fetch carts")
            //this.carts = this.debugCarts
            //this.show_popup = true
            //this.popupHeader =  "Login failure"
            //this.popupText = result.message
            this.show_popup = true
            this.popupHeader =  "Internal Server Error"
            this.popupText = "Something went wrong"
        }
      }).catch(error => {
          this.show_popup = true
          this.popupHeader =  "Internal Server Error"
          this.popupText = "Something went wrong"
          this.$dbg_console_log(error)
      })
    },
    markPaid() {
      //this.$dbg_console_log(this.paid);
      this.$dbg_console_log("mark paid");
      this.cart.paid = !this.cart.paid;
    },
    addCart(newCart) {
      this.cart = [...this.carts, newCart];
    }
  },
  created() {
    this.fetchNewCarts()
    this.timer = setInterval(this.fetchNewCarts, 5000)
  },
  destroyed() {
    // stop fetching carts when navigate away
    clearInterval(this.timer)
  }
};
</script>
