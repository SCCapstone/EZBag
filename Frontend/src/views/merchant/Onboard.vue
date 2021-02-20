<template>
  <v-container>
    <form>
      <v-text-field
        v-model="name"
        :error-messages="nameErrors"
        :rules="[rules.required]"
        label="Business Name"
        required
        @input="$v.name.$touch()"
        @blur="$v.name.$touch()"
        id="businessName"
      ></v-text-field>
      <v-text-field
        v-model="address"
        :error-messages="addressErrors"
        :rules="[rules.required]"
        label="Street Address"
        required
        @input="$v.name.$touch()"
        @blur="$v.name.$touch()"
        id="address"
      ></v-text-field>
      <v-text-field
        v-model="address"
        :error-messages="addressErrors"
        :rules="[rules.required]"
        label="City"
        required
        @input="$v.name.$touch()"
        @blur="$v.name.$touch()"
        id="city"
      ></v-text-field>
      <v-select
            :items="states"
            :rules="[rules.required]"
            label="State"
            id="state"
      ></v-select>
      <v-select
            :items="countries"
            :rules="[rules.required]"
            label="Country"
            id="country"
      ></v-select>
      <v-text-field
        v-model="email"
        :error-messages="emailErrors"
        :rules="[rules.required]"
        label="E-mail"
        required
        @input="$v.email.$touch()"
        @blur="$v.email.$touch()"
        id="email"
      ></v-text-field>
      <v-text-field
        v-model="phone"
        :error-messages="phoneErrors"
        :rules="[rules.required]"
        label="Phone Number"
        :counter="10"
        required
        @input="$v.phone.$touch()"
        @blur="$v.phone.$touch()"
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
        :error-messages="checkboxErrors"
        label="Do you agree?"
        id="check"
        required
        @change="$v.checkbox.$touch()"
        @blur="$v.checkbox.$touch()"
      ></v-checkbox>
      <div class="center">
        <v-btn
          class="mr-4"
          @click="submit"
          @click:clear="Confirm()"
          id="submit"
        >
          submit
        </v-btn>
      </div>
    </form>
  </v-container>
</template>

<script>
  export default {
    name: 'onboard',

    data: () => ({
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
      password: '',
      rules: {
        required: value => !!value || 'Required.',
        min: v => v.length >= 8 || 'Min 8 characters',
        //equal: ,
      },
  
    }),

    methods: {
      Confirm(){
        var password = document.getElementById("password").value
        var confirmPassword = document.getElementById("confirmPassword").value
        if (password != confirmPassword) {
          alert("Passwords do not match!")
          return false
        }
        return true
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
