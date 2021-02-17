<template>
  <div id="store">
    <Carts v-bind:carts="carts" v-on:verify="markPaid" />
  </div>
</template>

<script>
import Carts from '@/components/Carts';
export default {
  name: 'store',
  components: {
    Carts
  },
  markPaid() {
        this.cart.paid = !this.cart.paid;
  },
  data(){
    return {
      carts: [
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
          barcodes:[],
          quantities:[],
          businessID: 2,
          subtotal: 2,
          session:1112,
          tax: 2,
          total: 4,
          time: 2,
          cartHash:"",
          paid:false,
        },
        {
          barcodes:[],
          quantities:[],
          businessID: 3,
          subtotal: 2,
          session:1113,
          tax: 2,
          total: 4,
          time: 2,
          cartHash:"",
          paid: false,
        }
      ]
    }
  }
}
</script>
