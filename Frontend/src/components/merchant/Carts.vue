<template>
  <v-container class="pt-0 px-0 mt-n3">
    <v-card
      color="primary"
      class="rounded-0 elevation-0">
      <v-btn-toggle
        v-model="toggle_exclusive"
        mandatory
        class="ml-4 mb-2"
        light>
        <v-btn
          ripple 
          small 
          light 
          @click="selectionChanged('Verified')" active-class="button-active">
          Verified
        </v-btn>
        <v-btn 
          ripple
          small
          light
          @click="selectionChanged('Unverified')" active-class="button-active">
          Unverified
        </v-btn>
      </v-btn-toggle>
    </v-card>
    <v-expansion-panels
      class="px-3">
      <v-expansion-panel
        v-bind:key="cart.cartHash"
        v-for="cart in carts.slice().reverse()"
        class="carts">
        <v-expansion-panel-header
          v-if="cart.verified==(currentView=='Verified')">
          <v-row>
            <v-col>
              <v-icon>mdi-cart</v-icon>
              {{cart.cartHash.substring(cart.cartHash.length - 3)}}
              <br/>
            </v-col>
            <v-row>
            </v-row>
          </v-row>
          <v-col>
            ${{showTwoDecimal(cart.total)}}
          </v-col>
          <v-col>{{cart.dt}}</v-col>
          <v-col 
            v-if="!cart.verified">
            <v-btn
              small
              @click="markPaid(cart)">
              Verify
            </v-btn>
          </v-col>
        </v-expansion-panel-header>
        <v-expansion-panel-content
          v-if="cart.verified==(currentView=='Verified')">
          <v-row>
            <v-col>Name</v-col>
            <v-col>Quantity</v-col>
          </v-row>
          <v-row
            v-for="(name, index) in cart.names"
            :key=name>
              <v-col>{{name}}</v-col>
              <v-col>({{cart.quantities[index]}})</v-col>
          </v-row>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
  </v-container>
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
      headers: [""],
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