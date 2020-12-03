<template>
    <v-btn
    x-large
    fixed bottom
    rounded
    id="TotalButton"
    :style="{left : '50%', transform:'translateX(-50%)'}"
    >${{total}}</v-btn>   
</template>

<script>
export default {
    name: 'ScanButtons',
    props: {
        total: Number
    }
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