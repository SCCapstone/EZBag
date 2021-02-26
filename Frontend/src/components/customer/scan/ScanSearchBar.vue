<template>
  <v-container>

    <v-autocomplete
        clearable
        filled
        rounded
        solo-inverted
        v-model="model"
        :items="items"
        :loading="isLoading"
        :search-input.sync="search"
        color="white"
        hide-no-data
        hide-selected
        item-text="Product"
        item-value="Name"
        label="Search Products"
        placeholder="Scan or start typing to search"
        return-object
      ></v-autocomplete>

  </v-container>

</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'

export default {
  name: 'SearchPage',
    mounted() {

    },
    components: {

    },

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
      ...mapGetters(['getBusinessID']),
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
      ...mapActions(["fetchProducts"]),
      ...mapMutations([
                      "setBusinessID"
                    ]),
    },
    watch: {
      search () {
        
        if (this.model && !this.model.barcode == undefined) {
            this.$emit("showproduct", this.model.barcode)   
        }

        // Items have already been loaded
        if (this.items.length > 0) return

        // Items have already been requested
        if (this.isLoading) return

        this.isLoading = true

        // TODO: front load products and store in vuex
        console.log(this.getBusinessID)
        this.fetchProducts(this.getBusinessID)
          .then((result) => { // no backend errors thrown
          this.$dbg_console_log(result)
            if(result.success==1) {
                console.log("Success")
                this.count = result.count
                this.products = result.products
                console.log(this.products)
            } else {
                console.log("Failed to get products")
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