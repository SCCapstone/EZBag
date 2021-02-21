<template>
  <v-container>
    <div id="wrapper">
        <v-dialog
        v-model="show_popup"
        persistent
        max-width="290"
        >
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
                @click="show_popup = false"
            >
                OK
            </v-btn>
            </v-card-actions>
        </v-card>
        </v-dialog>
        <form>
            <div class="center" id="headerText">
            Welcome to EZBag<br><br>
            </div>
            <div class="center" style="font-size: 18px;">
                Login
            </div>
            <v-text-field
            v-model="email"
            :rules="[rules.required]"
            label="E-mail"
            required
            id="email"
            ></v-text-field>
            <v-text-field
            v-model="password"
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="[rules.required, rules.min]"
            :type="show1 ? 'text' : 'password'"
            label="Password"
            hint="At least 8 characters required"
            id="password"
            @click:append="show1 = !show1"
            ></v-text-field>
            <div class="center">
            <v-btn
                class="mr-4"
                @click="submit"
                id="submit"
            >
                Login
            </v-btn>
            </div>
        </form>
        <div>
            <br>
            New to EZBag? <router-link to="/onboard">Sign up</router-link>
        </div>
    </div>
  </v-container>
</template>

<script>
  import {mapActions} from 'vuex';
  export default {
    name: 'login',

    data: () => ({

      
      password: "",
      email: "",

      popupHeader: "Login failure",
      popupText: "Something went wrong",
      show_popup: false,

      show1: false,
      show2: false,

      rules: {
        required: value => !!value || 'Required.',
        min: v => v.length >= 8 || 'Min 8 characters',
        //equal: ,
      },
  
    }),

    methods: {
      ...mapActions(["loginUser"]),
      submit(){
        this.loginUser({
            "email":this.email,
            "password":this.password
            }).then((result) => { // no backend errors thrown
            this.$dbg_console_log(result)
            if(result.success==1) {
                //TODO handle successful login
                console.log("Successful login")
            }else{
                this.show_popup = true
                this.popupHeader =  "Login failure"
                this.popupText = result.message
            }
            }).catch(error => {
                this.show_popup = true
                this.popupHeader =  "Internal Server Error"
                this.popupText = "Something went wrong"
                console.log(error)
            })
            //*/
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

  .v-btn {
    border-radius: 30px;
    height: 110%;
  }
</style>
