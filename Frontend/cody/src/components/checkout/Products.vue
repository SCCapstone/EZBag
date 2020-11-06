<template>
  <div>
    <h3>Products</h3>
    <div class="products">
      <div v-for="product in allProducts" v-bind:key="product.id" class="product">
        {{ product.name }}
        {{ product.price}}
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name:"Products",
  computed: mapGetters(['allProducts'])
}
</script>

<style>

</style>