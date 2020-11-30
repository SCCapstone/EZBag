<template>
  <div>
    <div class="scanner" id="scandit-barcode-picker"></div>
    <ScanButtons 
      v-bind:total=getCartSubtotal />
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import ScanButtons from '@/components/ScanButtons'

export default {
  name: 'Scanner',
  components: {
    ScanButtons,
  },
  computed: mapGetters(['getCartSubtotal'])
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .scanner {
      height: 95vh; /* For 100% screen height */
      width:  100vw; /* For 100% screen width */
      margin: 0;
      position: absolute;
      top: 50%;
      left: 50%;
      -ms-transform: translate(-50%, -50%);
      transform: translate(-50%, -50%);
  }
</style>