<template>
    <header class="header">
      <h2>Merchant</h2>
      <div id="nav">
        <router-link to="/">Store</router-link> |
        <router-link to="/stats">Stats</router-link>
      </div>
    </header>
</template>
<script>
export default {
  name: "Header"
}
</script>
<style scoped>
 .header{
     background:gray;
     color:#fff;
     text-align: center;
     padding: 10px;
 }
 .header a {
     color: #fff;
     padding-right: 5px;
     text-decoration: none;
 }
</style>
