<template>
    <v-btn
    x-large
    fixed bottom
    rounded
    id="TotalButton"
    @click="clickedTotalBtn"
    :style="{left : '50%', transform:'translateX(-50%)'}"
    >${{showTwoDecimal(total)}}</v-btn>   
</template>

<script>
export default {
    name: 'ScanButtons',
    props: {
        total: Number
    },
    methods: {
        showTwoDecimal(num) {		
            return (num).toFixed(2);
        },
        clickedTotalBtn() {
            // https://stackoverflow.com/questions/59770610/uncaught-in-promise-in-vue-router-when-multiple-clicks-on-a-button
            this.$router.push('cart').catch(error => {console.error(error);});
        }
    },
}
</script>


<style scoped>
    .btn-bot-left {
        position: absolute;
        display: table;
        text-align: center;
        border-radius: 20px;
        left: 33%;
        width: 33%;
        height: 7%;
        margin-left: -16.5%;
        bottom: 3%;
        background: #FAFAFA;
    }
    .btn-bot-right {
        position: absolute;
        display: table;
        text-align: center;
        border-radius: 20px;
        left: 66%;
        width: 33%;
        height: 7%;
        margin-left: -16.5%;
        bottom: 3%;
        background: #E0E0E0;
    }

    .inner{
        display: table-cell;
        vertical-align: middle;
    }
</style>