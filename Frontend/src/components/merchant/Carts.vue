<template>
  <div>
    <div v-bind:key="cart.businessID" v-for="cart in carts" class="carts">
      <div class="cart" v-bind:class="{'is-paid':cart.paid}
      ">
        <h4>Cart {{cart.businessID}}</h4>
        <small>
            <p> 
              <v-btn @click="markPaid(cart)">Verify</v-btn>
              Subtotal: {{cart.subtotal}}
              Tax: {{cart.tax}}
              Total: {{cart.total}} 
            </p> 
        </small>
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
      markPaid(cart) {
        console.log(cart)
        cart.paid = !cart.paid
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
      padding:1px;
      border-bottom: 1px #ccc dotted;
  }

  .is-paid {
    background: #d9ead3;
    padding: 3px;
  }

  .v-btn {
    height: 110%;
    position: absolute;
    right: 0px;
    margin-top: -15px;
    margin-right: 10px;
  }
</style>