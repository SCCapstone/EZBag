<template>
  <div>
    <v-app-bar height="40" color="primary" dark absolute class="menu">
      <v-btn-toggle
          v-model="toggle_exclusive"
          mandatory
          class = "toggle"
          light
        >
        <v-btn ripple small light @click="selectionChanged('Verified')" active-class="button-active">
          Verified
        </v-btn>
        <v-btn ripple small light @click="selectionChanged('Unverified')" active-class="button-active">
          Unverified
        </v-btn>
    </v-btn-toggle>
    </v-app-bar>
    <div v-bind:key="cart.cartHash" v-for="cart in carts.slice().reverse()" class="carts">
      <div v-if="cart.verified==(currentView=='Verified')" @click="expandCart(cart)" class="cart" v-bind:class="{'is-paid':cart.verified}
      ">
        <h4><v-icon>mdi-cart</v-icon>{{cart.cartHash.substring(cart.cartHash.length - 3)}}</h4>
        <h5> {{cart.dt}} </h5>
        <small>
            <p> 
              <v-btn v-on:click.stop @click="markPaid(cart)" v-if="!cart.verified" class="verify">Verify</v-btn>
              <u v-if="cart.expanded==true">
                <v-icon class="chevron" size=50>mdi-chevron-up</v-icon>
              </u>
              <u v-else>
                <v-icon class="chevron" size=50>mdi-chevron-down</v-icon>
              </u>
              <b>Total: ${{showTwoDecimal(cart.total)}}</b>
            </p>
        </small>
        <div v-if="cart.expanded==true">
          <div class="cartItem" v-for="(name, index) in cart.names" :key=name>
            ({{cart.quantities[index]}}) {{name}}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import {mapActions} from 'vuex';

export default {
    name: "Carts",
    props:["carts"],
    components: {
    },
    data: () => ({
      items: [
        { title: 'Verified' },
        { title: 'Unverified' },
      ],
      currentView: "Verified",
      toggle_exclusive: true,
    }),
    methods: {
      ...mapActions(["verifyCart"]),
      selectionChanged(btn) {
        console.log(btn)
        this.currentView = btn
      },
      showTwoDecimal(num) {		
        return (num).toFixed(2);
      },
      expandCart(cart){
        cart.expanded = !cart.expanded
      },
      markPaid(cart) {
        var cartHash = cart.cartHash
        this.verifyCart({businessID:this.$route.params.id, cartHash:cartHash})
        .then((result) => { // no backend errors thrown
        this.$dbg_console_log(result)
        if(result.success==1) {
            cart.verified = true
        } else {
            this.$dbg_console_log("Failed to mark cart as paid")
        }
        }).catch(error => {
            this.$dbg_console_log(error)
        })
      },
    }
}
</script>
<style scoped>
  .menu {
    position: relative;
    top: 0px;
    background-color: rgb(53, 118, 203);
    right: 0px;
    height: 20px;
    width: 100%;
  }
  .text-center{
    text-align: center;
  }
  .carts {
      background: #f4cccc;
      border-bottom: 1px #FFFFFF dotted;
      text-indent: 5px;
  }

  .is-paid {
    background: #d9ead3;
    border-bottom: 1px #FFFFFF dotted;
  }

  .button-active
  {
    
    color: black;
  }

  .cartItem {
    width: 100%;
    height: 40px;
    border-top: 1px #FFFFFF double;
  }

  .chevron
  {
    position: absolute;
    right: 5px;
    margin-top: -21px;
  }
  .verify {
    height: 30%;
    position: absolute;
    right: 0px;
    margin-top: -13px;
    margin-right: 55px;
  }
  .toggle {
    left: 0px;
    top: -3px;
  }
  
</style>