<template>
  <v-container>
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
      <v-text-field
        v-model="name"
        :rules="[rules.required]"
        label="Business Name"
        required
        id="businessName"
      ></v-text-field>
      <v-text-field
        v-model="address"
        :rules="[rules.required]"
        label="Street Address"
        required
        id="address"
      ></v-text-field>
      <v-text-field
        v-model="city"
        :rules="[rules.required]"
        label="City"
        required
        id="city"
      ></v-text-field>
      <v-select
            v-model="state"
            :items="states"
            :rules="[rules.required]"
            label="State"
            id="state"
      ></v-select>
      <v-select
            v-model="country"
            :items="countries"
            :rules="[rules.required]"
            label="Country"
            id="country"
      ></v-select>
      <v-text-field
        v-model="email"
        :rules="[rules.required, rules.emailRules]"
        label="E-mail"
        required
        id="email"
      ></v-text-field>
      <v-text-field
        v-model="phone"
        :rules="[rules.required]"
        label="Phone Number"
        :counter="10"
        required
        id="phone"
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
      <v-checkbox
        class="check"
        v-model="checkbox"
        label="Do you agree?"
        id="check"
        required
      ></v-checkbox>
      <div class="center">
        <v-btn
          class="mr-4"
          @click="submit"
          id="submit"
        >
          submit
        </v-btn>
      </div>
    </form>
  </v-container>
</template>

<script>
  import {mapActions} from 'vuex';
  export default {
    name: 'register',

    data: () => ({
      name: "",
      address: "",
      city: "",
      password: "",
      phone: "",
      email: "",
      state: "",
      country: "",
      checkbox: false,

      popupHeader: "Internal Error",
      popupText: "Something went wrong",
      show_popup: false,


      states: ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California', 
          'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 
          'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 
          'Kentucky', 'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 
          'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana', 
          'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 
          'New York', 'North Carolina', 'North Dakota', 'Ohio', 'Oklahoma', 
          'Oregon', 'Pennsylvania', 'Rhode Island', 'South Carolina', 
          'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont', 
          'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'],
        
      countries: ['United States'],

      show1: false,
      show2: false,
      rules: {
        required: value => !!value || 'Required.',
        min: v => v.length >= 8 || 'Min 8 characters',
        // emailRules credit to user @ https://stackoverflow.com/questions/50039793/email-validation-n-vuetify-js
        emailRules: v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail must be valid',
        //equal: ,
      },
  
    }),

    methods: {
      ...mapActions(["registerUser"]),
      submit() {
        if (!this.name
            || !(this.address)
            || !(this.city)
            || !(this.state)
            || !(this.country)
            || !(this.email)
            || !(this.phone)
            || !(this.password)
            || this.password.length < 8
            || !(/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(this.email))
            || !(this.checkbox))
        {
          console.log("ERROR: Register field requirements have not been met.")
          this.show_popup = true
          this.popupHeader =  "Oops!"
          this.popupText = "Field requirements have not been met."
          return false
        } else {
          console.log(!(this.rules.min(this.password))
            || !(this.rules.emailRules(this.email)))
            console.log("Registering user!")
            this.registerUser({
              "businessName":this.name,
              "streetAddress":this.address,
              "country": this.country,
              "city": this.city,
              "state": this.state,
              "email": this.email,
              "phone": this.phone,
              "password": this.password
            }).then((result) => { // no backend errors thrown
              this.$dbg_console_log(result)
              if(result.success==1) {
                this.$router.push('registrationSuccess');
              }else{
                this.show_popup = true
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
  }
  /*
  let businessName = document.getElementById("businessName").value;
  let address = document.getElementById("address").value;
  let city = document.getElementById("city").value;
  let state = document.getElementById("state").value;
  let zip = document.getElementById("zip").value;
  let email = document.getElementById("email").value;
  let phone = document.getElementById("phone").value;
  let check = document.getElementById("check").value;
  let button = document.getElementbyId("submit");
  */

</script>
<style scoped>
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
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .v-btn {
    border-radius: 30px;
    height: 110%;
  }
</style>
