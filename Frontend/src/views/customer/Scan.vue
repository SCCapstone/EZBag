<template>
  <div>
    <Scanner />
  </div>
</template>

<script>
  import Scanner from '@/components/customer/scan/Scanner'
  import { mapMutations } from 'vuex';

  export default {
    name: 'Scan',
    components: {
      Scanner,
    },
    mounted() {
      this.$emit("toggleHeader", "customer")
      // set businessID
      this.initBusinessID()
    },
    methods: {
      ...mapMutations([
                      "setBusinessID"
                    ]),
      initBusinessID() {
        var storage = window.localStorage
        // check local storage for redirect id
        if (storage.getItem("redirectID") === null) {
           // if does not exist, set to hardcoded development value
          console.log("ERROR: Cannot find redirectID, using development value!")
          this.setBusinessID("179aa3e0fb88f6e4ec0ef0d0f5588d43f93713e7b7e4a5ddd8a3fdd1c39701fa")
        } else {
          console.log("Found redirectID!")
          this.setBusinessID(storage.getItem("redirectID"))
        }
      },
    },
    data: () => ({
      
    }),
  };
</script>
