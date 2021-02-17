<template>
  <div>
    <div v-bind:key="cart.id" v-for="cart in carts" class="carts">
      <div class="cart" v-bind:class="{'is-paid':cart.paid}
      ">
        <h4>Cart {{cart.id}}</h4>
        <Contents />
        <p>
          <button @click="$emit('verify',carts.id)" class="verify">Verify</button>
        </p>
      </div>
    </div>
  </div>
</template>
<script>
    import Contents from '@/components/Contents.vue'
export default {
    name: "Carts",
    props:["carts"],
    components: {
        Contents
    }, 
}
</script>
<style scoped>
  .carts {
      background: #e1dfe1; 
      padding:10px;
      border-bottom: 1px #ccc dotted;
  }

  .is-paid {
    background: lime;
    padding: 3px;
  }

  .verify { 
    padding: 1px;
  }
</style>