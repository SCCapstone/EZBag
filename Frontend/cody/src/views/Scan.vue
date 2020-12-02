<template>
  <Scanner />
</template>

<script>
import Scanner from '@/components/scan/Scanner'
export default {
  name: 'Scan',
  created() {
    document.getElementById("scandit-barcode-picker").style.display = "inline"
  },
  beforeDestroy() {
    document.getElementById("scandit-barcode-picker").style.display = "none"
  },
  components: {
    Scanner,
  },
  data: () => ({
    
  }),
};
</script>
