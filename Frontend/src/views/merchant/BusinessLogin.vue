<template>
  <v-container id="wrapper">
    <v-dialog
      v-model="show_popup"
      persistent
      max-width="290">
      <v-card>
        <v-card-title class="headline">
          {{popupHeader}}
        </v-card-title>
          <v-card-text>{{popupText}}</v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              justify="center"
              color="green darken-1"
              text
              @click="show_popup = false">
              OK
            </v-btn>
          </v-card-actions>
      </v-card>
    </v-dialog>
    <h2
      class="text-center font-weight-regular my-5">
      Welcome to EZBag
    </h2>
    <v-form 
      v-model="isLoginFormValid"
      @submit.prevent="submit">
      <v-text-field
        v-model="email"
        :rules="emailRules"
        label="E-mail"
        required
        id="email">
      </v-text-field>
      <v-text-field
        v-model="password"
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="passwordRules"
        :type="showPassword ? 'text' : 'password'"
        label="Password"
        id="password"
        @click:append="showPassword = !showPassword">
      </v-text-field>
    <v-row>
      <v-col>
        <v-btn
          rounded
          block
          to="/register">
          Register
        </v-btn>
      </v-col>
      <v-col>
        <v-btn
          rounded
          block
          color="primary"
          :disabled="!isLoginFormValid"
          type="submit">
          Login
        </v-btn>
      </v-col>
    </v-row>
    </v-form>
  </v-container>
</template>

<script>
  import {mapActions, mapMutations} from 'vuex';
  import Vue from 'vue'
  import VueCookies from 'vue-cookies';
  Vue.use(VueCookies);
  export default {
    name: 'login',

    data: () => ({
      password: "",
      email: "",

      popupHeader: "Login failure",
      popupText: "Something went wrong",
      show_popup: false,
      isLoginFormValid: false,
      // credit to https://blog.logrocket.com/how-to-implement-form-validation-with-vuetify-in-a-vue-js-app/
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /^(([^<>()[\]\\.,;:\s@']+(\.[^<>()\\[\]\\.,;:\s@']+)*)|('.+'))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v) || 'E-mail must be valid',
      ],
      passwordRules: [
        v => !!v || 'Password is required',
        v => v.length >= 8 || 'Password must be at least 8 characters long',
      ],
      showPassword: false,
    }),
    methods: {
      ...mapActions(["loginUser"]),
      ...mapMutations(["setBusinessID"]),
      submit(){
        this.loginUser({
          "email":this.email,
          "password":this.password
        })
        .then((result) => { // no backend errors thrown
          this.$dbg_console_log(result)
          if(result.success==1) {
            this.$dbg_console_log("TOKEN: "+result.token)
            this.setBusinessID(result.businessID)
            this.$cookies.set("token", result.token, {secure: true, expires: 99983090})
            this.$router.push('merchant/'+result.businessID+'/store')
            this.$dbg_console_log("Successful login")
          } else {
            this.show_popup = true
            this.popupHeader =  "Login failure"
            this.popupText = result.message
        }})
        .catch(error => {
          this.show_popup = true
          this.popupHeader =  "Internal Server Error"
          this.popupText = "Something went wrong"
          this.$dbg_console_log(error)})
        return true
      }
    }
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
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>
