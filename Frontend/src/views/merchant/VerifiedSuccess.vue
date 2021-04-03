<template>
  <v-container>
    <div id="wrapper">
        <div id="headerText" class="center">
            You have successfully verified your account!<br>
            <br>
        </div>
        <div id="subHeaderText" class="center">
            Please close this browser tab and then proceed to login.<br>
        </div>
    </div>
  </v-container>
</template>

<script>
  export default {
    name: 'emailVerifiedSuccess',

  }

</script>
<style scoped>
  #wrapper {
      margin-top: 10%;
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
