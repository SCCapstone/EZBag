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
      this.$emit("toggleHeader", true)
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
          this.setBusinessID("cad1ab052ffff19ff3f595c569f7a37f826921d07c4262946d81ef04ec72d727")
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
