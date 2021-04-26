<template>
    <div>
        <div class = "center">
            <h1>404</h1>
            <h2>PAGE NOT FOUND</h2><br>
            <v-btn @click="goHome()">Home</v-btn>
        </div>
    </div>
</template>

<script>
    export default {
        name: "NotFound",
        methods: {
            goHome() {
                this.$router.push("/")
            }
        }
    }
</script>

<style scoped>
    .center {
        text-align: center;
        position: absolute;
        top: 50%;
        left: 50%;
        -ms-transform: translate(-50%, -50%);
        transform: translate(-50%, -50%);
    }
</style>