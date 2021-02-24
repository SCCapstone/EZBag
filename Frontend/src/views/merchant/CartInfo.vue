<template>
  <v-container ma-0 pa-0>
    <div v-for="Cart in Carts" v-bind:key="Cart.cartHash" class="header">
      <h class="cartHash">Cart {{ Cart.cartHash }}</h>

      <p style="margin-bottom: 3px;">{{ Cart.time }}</p>
      <p style="margin-bottom: 3px;" class="total">Total: ${{ Cart.total }}</p>
      <p style="margin-bottom: 3px;" class="quantity">Items: {{ Cart.quantity }}</p>
    </div>

    <div class="items">
      <v-row
        v-for="product in Products"
        v-bind:key="product.barcode"
        class="products"
        no-gutters
      >
        <v-col>
          <v-card class="product">
            <v-card-title>{{ product.name }}</v-card-title>
            <v-card-actions>
              <span class="subheading ml-2">${{ product.price }}</span>
              <v-spacer></v-spacer>
              <span class="subheading mr-2 ml-2">{{ product.quantity }}</span>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </div>

    <div class="center">
        <v-btn
          class="mr-4"
          @click="verify"
          id="verify"
        >
          verify
        </v-btn>
    </div>
  </v-container>
</template>

<script>
  export default {
    name: 'CartInfo',

    data(){
      return {
        Carts: [
          { cartHash: '10', time: '', total: 10.99, quantity: 3 }
        ],

        Products: [
          { barcode: 'a', name: 'Salt', price: 3.99, quantity: 1 },
          { barcode: 'b', name: 'Salt', price: 3.99, quantity: 1 },
          { barcode: 'c', name: 'Salt', price: 3.99, quantity: 1 }
        ]
      }
    }
  }
</script>

<style scoped>
  .v-btn {
    border-radius: 30px;
    height: 110%;
  }

  .center {
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    bottom: 20px;
    left: 0;
    right: 0;
  }

  .v-container {
    padding: 0;
    margin: 0;
    height: 100%;
    width: 100%;
  }

  .header {
    padding: 5px;
    background-color: #e0e0e0;
  }

  .v-card__text, .v-card__title {
    word-break: normal;
  }

  .info {
    margin-bottom: 2px;
  }

  .cartHash {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 0;
  }

  .product {
    margin-bottom: -12px;
  }

  .total, .quantity {
    display: inline-flex;
  }

  .quantity {
    float: right;
    margin-right: 20%;
  }
</style>