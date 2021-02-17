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
          barcodes:[],
          quantities:[],
          session:1111,
          id: 1,
          paid:true,
        },
        {
          barcodes:[],
          quantities:[],
          session:1112,
          id: 2,
          paid:false,
        },
        {
          barcodes:[],
          quantities:[],
          session:1113,
          id: 3,
          paid: false,
        }
      ]
    }
  }
}
</script>
