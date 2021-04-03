<template>
  <div class="splash">
    <div id="wrapper">
      <div class="center" id="headerText">
            Welcome to EZBag<br><br>
      </div>
        <div class="center">
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            Go to
        </div>
        <div class="center">
            <router-link to="/login"> Merchant Login</router-link>
        </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
  #wrapper {
      margin-top: 13%;
  }
  #headerText {
    font-size: 24px;
  }
  #subHeaderText {
    font-size: 18px;
  }
  .v-text-field {
    margin: 0px;
  }

  .v-select {
    margin: 0px;
  }

  .check {
    margin: 10px;
  }

  .center {
    width: 100%;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .v-btn {
    border-radius: 30px;
    height: 110%;
  }

  
</style>