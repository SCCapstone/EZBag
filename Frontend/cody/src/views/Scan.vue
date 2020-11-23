<template>
  <div>
    <Scanner />
    <ScanButtons />
  </div>
</template>

<script>
import Scanner from '@/components/Scanner'
import ScanButtons from '@/components/ScanButtons'
export default {
  name: 'Scan',
  components: {
    Scanner,
    ScanButtons,
  },
  data: () => ({
    
  }),
};
</script>

