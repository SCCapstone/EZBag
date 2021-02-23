<template>
  <div id="store">
    <Carts v-bind:carts="carts" v-on:verify="markPaid" />
  </div>
</template>

<script>
import Carts from "@/components/merchant/Carts";
export default {
  name: "store",
  components: {
    Carts,
  },
  mounted() {
    this.$emit("toggleHeader", "business")
  },
  data() {
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
          total: 4,
          time: 2,
          cartHash: "",
          paid: false,
        },
      ],
    };
  },
  methods: {
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
