<template>
  <div id="store">
    <p>Hello World</p>
  </div>
</template>

<script>
    export default {
        name: "statistics",
        components: {
            
        },
        data() {
            return {
            
            };
        },
        mounted() {
            this.$emit("toggleHeader", "business")
        },
        methods: {
            
        },
    };
</script>
