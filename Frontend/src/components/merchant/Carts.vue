<template>
  <div>
    <div v-bind:key="cart.cartHash" v-for="cart in carts" class="carts">
      <div @click="test(cart)" class="cart" v-bind:class="{'is-paid':cart.verified}
      ">
        <h4><v-icon>mdi-cart</v-icon>{{cart.cartHash.substring(cart.cartHash.length - 3)}}</h4>
        <small>
            <p> 
              <v-btn v-on:click.stop @click="markPaid(cart)">Verify</v-btn>
              <u v-if="cart.expanded==true">
                <v-icon class="chevron" size=50>mdi-chevron-up</v-icon>
              </u>
              <u v-else>
                <v-icon class="chevron" size=50>mdi-chevron-down</v-icon>
              </u>
              Total: {{cart.total}}
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
export default {
    name: "Carts",
    props:["carts"],
    components: {
    }, 
    methods: {
      test(cart){
        cart.expanded = !cart.expanded
        console.log("working")
      },
      markPaid(cart) {
        console.log(cart)
        cart.verified = !cart.verified
        //this.$dbg_console_log(this.paid);
        this.$dbg_console_log('mark paid');
        //this.cart.paid = !this.cart.paid;
      },
    }
}
</script>
<style scoped>
  .carts {
      background: #f4cccc;
      border-bottom: 1px #FFFFFF dotted;
      text-indent: 5px;
  }

  .is-paid {
    background: #d9ead3;
    border-bottom: 1px #FFFFFF dotted;
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


  .v-btn {
    height: 30%;
    position: absolute;
    right: 0px;
    margin-top: -13px;
    margin-right: 55px;
  }
</style>