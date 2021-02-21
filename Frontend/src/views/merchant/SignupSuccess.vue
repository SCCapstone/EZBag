<template>
  <v-container>
    <div id="wrapper">
        <div id="headerText" class="center">
            Successfully submitted application<br>
        </div>
        <div class="center">
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            Return to
        </div>
        <div class="center">
            <router-link to="/login"> login</router-link>
        </div>
    </div>
  </v-container>
</template>

<script>
  export default {
    name: 'signupSuccess',

  }

</script>
<style scoped>
  #wrapper {
      margin-top: 10%;
  }
  #headerText {
    font-size: 24px;
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
