<template>
  <v-container>
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
      class="text-center font-weight-regular mb-6">
      Welcome to EZBag
    </h2>
    <v-form 
      v-model="isRegisterFormValid"
      @submit.prevent="submit">
      <v-text-field
        v-model="name"
        :rules="nameRules"
        label="Business Name"
        :counter="60"
        id="businessName">
        </v-text-field>
      <v-text-field
        v-model="address"
        :rules="addressRules"
        label="Street Address"
        :counter="60"
        id="address">
      </v-text-field>
      <v-text-field
        v-model="city"
        :rules="cityRules"
        label="City"
        :counter="60"
        id="city">
      </v-text-field>
      <v-select
        v-model="state"
        :items="states"
        :rules="stateRules"
        label="State"
        id="state">
      </v-select>
      <v-select
        v-model="country"
        :items="countries"
        :rules="countryRules"
        label="Country"
        id="country">
      </v-select>
      <v-text-field
        v-model="phone"
        :rules="phoneRules"
        label="Phone Number"
        id="phone">
      </v-text-field>
      <v-text-field
        v-model="email"
        :rules="emailRules"
        label="E-mail"
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
      <v-checkbox
        v-model="checkbox"
        :true-value="1"
        :false-value="0"
        :rules="[v => v == 1 || 'You must agree to continue!']">
        <template v-slot:label>
          <div @click.stop="">
            Do you accept the
            <a
              href="#"
              @click.prevent="terms = true">
              terms
            </a>
            and
            <a
              href="#"
              @click.prevent="conditions = true">
              conditions?
            </a>
          </div>
        </template>
      </v-checkbox>
      <v-row>
        <v-col>
          <v-btn
            rounded
            block
            to="/login">
            Login
          </v-btn>
        </v-col>
        <v-col>
          <v-btn
            rounded
            block
            color="primary"
            :disabled="!isRegisterFormValid"
            type="submit">
            Register
          </v-btn>
        </v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script>
  import {mapActions} from 'vuex';
  export default {
    name: 'register',

    data: () => ({
      name: "",
      nameRules:[
        v => !!v || 'Business name is required',
        v => v.length <= 60 || 'Business name too long'
      ],
      address: "",
      addressRules: [
        v => !!v || 'Address is required',
        v => v.length <= 60 || 'Address too long'
      ],
      city: "",
      cityRules: [
        v => !!v || 'City is required',
        v => v.length <= 60 || 'City name too long'
      ],
      state: "",
      stateRules: [
        v => !!v || 'State is required'
      ],
      country: "",
      countryRules: [
        v => !!v || 'Country is required'
      ],
      phone: "",
      phoneRules: [
        v => !!v || 'Phone Number is required',
        v => /^(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$/.test(v) || "Invalid Phone Number"
      ],
      email: "",
      // credit to https://blog.logrocket.com/how-to-implement-form-validation-with-vuetify-in-a-vue-js-app/
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /^(([^<>()[\]\\.,;:\s@']+(\.[^<>()\\[\]\\.,;:\s@']+)*)|('.+'))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v) || 'E-mail must be valid',
      ],
      password: "",
      passwordRules: [
        v => !!v || 'Password is required',
        v => v.length >= 8 || 'Password must be at least 8 characters long',
      ],
      checkbox: false,

      popupHeader: "Internal Error",
      popupText: "Something went wrong",
      show_popup: false,
      showPassword: false,
      isRegisterFormValid: false,

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
    }),
    methods: {
      ...mapActions(["registerUser"]),
      submit() {
        this.$dbg_console_log("Registering user!")
        // remove formatting from phone number
        let phoneNumber = this.phone.replaceAll(/[().\s-]/ig, '')
        this.$dbg_console_log(phoneNumber)
        this.registerUser({
          "businessName":this.name,
          "streetAddress":this.address,
          "country": this.country,
          "city": this.city,
          "state": this.state,
          "email": this.email,
          "phone": this.phone,
          "password": this.password,
        }).then((result) => { // no backend errors thrown
          this.$dbg_console_log(result)
          if(result.success==1) {
            this.$router.push('/registrationSuccess');
          } else {
            this.show_popup = true
            this.popupText = result.message
          }
        }).catch(error => {
          this.show_popup = true
          this.popupHeader =  "Internal Server Error"
          this.popupText = "Something went wrong"
          this.$dbg_console_log(error)
        })   
        return true
      }
    }
  }
</script>
