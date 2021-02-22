<template>
  <div>
    <form @submit="addCart">
      <input type="text" name="cart" placeholder="ID ..." />
      <input type="submit" value="Submit" class="btn" />
    </form>
  </div>
</template>

<script>
export default {
  name: "AddCart",
  data() {
    return {
      barcodes: [],
      quantities: [],
      businessID: 1,
      subtotal: 2,
      session: 1111,
      tax: 2,
      total: 4,
      time: 2,
      cartHash: "",
      paid: true,
    };
  },
  methods: {
    addCart(e) {
      e.preventDefault();  
      const newCart = {
        barcodes: this.barcodes,
        quantities: this.quantities,
        businessID: this.businessID,
        subtotal: this.subtotal,
        session: this.session,
        tax: this.tax,
        total: this.total,
        time: this.time,
        cartHash: this.cartHash,
        paid: false,
      }
      //send to parent
      this.$emit('add-cart', newCart);
    },
  },
};
</script>

<style scoped>
form {
  display: flex;
}
input [type="text"] {
  flex: 10;
  padding: 5px;
}
input[type="submit"] {
  flex: 2;
}
</style>