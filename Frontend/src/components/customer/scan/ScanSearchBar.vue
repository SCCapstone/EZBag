<template>
  <v-container>

    <v-autocomplete
        clearable
        rounded
        dark
        filled
        solo
        v-model="model"
        :items="items"
        :loading="isLoading"
        :search-input.sync="search"
        color="white"
        hide-no-data
        hide-selected
        item-text="Product"
        item-value="Name"
        placeholder="Scan or start typing to search"
        return-object
      ></v-autocomplete>

  </v-container>

</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'SearchPage',
    // credit to example found here: https://vuetifyjs.com/en/components/autocompletes/
    data: () => ({
      nameLimit: 40,
      products: [],
      isLoading: false,
      model: null,
      search: null,

      popupHeader: "Internal Error",
      popupText: "Something went wrong",
      show_popup: false,

    }),
    computed: {
      fields () {
        if (!this.model) return []

        return Object.keys(this.model).map(key => {
          return {
            key,
            value: this.model[key] || 'n/a',
          }
        })
      },
      items () {
        return this.products.map(entry => {
          const Product = entry.name.length > this.nameLimit
            ? entry.name.slice(0, this.nameLimit) + '...'
            : entry.name

          return Object.assign({}, entry, { Product })
        })
      },
    },
    methods: {
      ...mapActions(["fetchProducts"])
    },
    watch: {
      search () {
        
        if (this.model) {
          this.$emit("showproduct", this.model.barcode)   
          this.model = null
        }

        // Items have already been loaded
        if (this.items.length > 0) return

        // Items have already been requested
        if (this.isLoading) return

        this.isLoading = true

        // TODO: front load products and store in vuex
        this.fetchProducts(this.$route.params.id)
          .then((result) => { // no backend errors thrown
            if(result.success==1) {
                this.$dbg_console_log('ScanSearchBar: Succesfully received products from backend', result.products)
                this.count = result.count
                this.products = result.products
            } else {
                this.$dbg_console_log('ScanSearchBar: Could not get products')
            }
          }).catch(error => {
              this.show_popup = true
              this.popupHeader =  "Internal Server Error"
              this.popupText = "Something went wrong"
              this.carts = this.debugCarts   
              this.$dbg_console_log(error)
          })
          .finally(() => (this.isLoading = false))
      },
    },
    
    
};
</script>