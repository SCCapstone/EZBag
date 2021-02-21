<template>
  <div>
    <Scanner />
  </div>
</template>

<script>
  import Scanner from '@/components/customer/scan/Scanner'
  export default {
    name: 'Scan',
    components: {
      Scanner,
    },
    mounted() {
      this.$emit("toggleHeader", true)
    },
    data: () => ({
      
    }),
  };
</script>
