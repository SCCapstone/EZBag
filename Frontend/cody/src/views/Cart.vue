<template>
  <CartList />
</template>

<script>
// @ is an alias to /src
import CartList from '@/components/checkout/CartList'

export default {
  name: 'Cart',

  components: {
    CartList
  },

  data: () => ({
    //
  }),
};
</script>
