<template>
  <v-container>
    <div v-show="show_popup==true">
      <v-row justify="center">
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
      </v-row>
    </div>
    <div
      class="text-center text-h4 font-weight-regular py-3">
      Checkout Successful
    </div>
    <div
      class="text-center text-h5 font-weight-regular pb-3">
      Your cart ID is:
    </div>
    <div
      class="text-center text-h4 pb-3">
      {{this.getCartHash.substring(this.getCartHash.length - 3)}}
    </div>
    <div
      class="text-center text-subtitle-1 font-weight-regular pb-6">
      Don't forget to show this on your way out.
    </div>
    <v-divider></v-divider>
    <div
      class="text-center text-h5 font-weight-regular py-3">
      Get your reciept!
    </div>
    <v-form 
      v-model="isPhoneValid"
      @submit.prevent="submit">
      <v-text-field
        v-model="phone"
        :rules="phoneRules"
        label="Phone Number"
        id="phone"
        class="pb-3">
      </v-text-field>
    </v-form>
      <div
        class="text-center text-h6 font-weight-regular">
        OR
      </div>
    <v-form 
      v-model="isEmailValid"
      @submit.prevent="submit">
      <v-text-field
        v-model="email"
        :rules="emailRules"
        label="E-mail"
        id="email">
      </v-text-field>
      <v-btn
        rounded
        block
        color="primary"
        :disabled="!isEmailValid && !isPhoneValid"
        type="submit">
        Send Reciept
      </v-btn>
    </v-form>
    <!-- <div class="centerText" id="headerText">
      Checkout successful<br>
      Your cart number is:
    </div>
    <div class="centerText" id="cartNumber">
      {{ this.getCartHash.substring(this.getCartHash.length - 3) }}
    </div>
    <div class="horizontalBar"></div>
    <div class="centerText" id="headerText">
      Enter your email and or phone number<br>
      to receive a digital receipt
    </div>
    <input id="emailForm" class="inputForm" placeholder="email">
    <input id="phoneForm" class="inputForm" type="number" placeholder="phone number">
    <button
      @touchstart="mobiledown"
      @mousedown="mousedown"
      @touchend="mobileup"
      @mouseup="mouseup"
      @click="mouseup"
      class="ovalButton button text"
      id="confirmButton">
          Confirm
    </button> -->
  </v-container>
</template>
<script>

// https://vuex.vuejs.org/guide/actions.html#dispatching-actions-in-components
import { mapGetters, mapActions } from 'vuex';
import Cookie from 'js-cookie'
export default {
  name:"Receipt",
  computed: mapGetters(['getCartHash']),
  mounted() {
    this.$store
      .dispatch("checkIfBusinessIDExists", this.$route.params.id)
      .then((response) => {
        var exists = response.exists;
        if (!exists) {
          this.$router.push("/notfound")
        }
      })
      .catch((error) => {
        console.log(error);
      });
  },
  data() {
    return {
      popupHeader: "Internal Error",
      popupText: "Something went wrong",
      show_popup: false,

      isPhoneValid:false,
      isEmailValid:false,
      phone: "",
      phoneRules: [
        v => !!v || !!this.email|| 'E-mail or phone number is required',
        v => /^(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$/.test(v) || "Invalid Phone Number"
      ],
      email: "",
      // credit to https://blog.logrocket.com/how-to-implement-form-validation-with-vuetify-in-a-vue-js-app/
      emailRules: [
        v => !!v || 'E-mail or phone number is required',
        v => /^(([^<>()[\]\\.,;:\s@']+(\.[^<>()\\[\]\\.,;:\s@']+)*)|('.+'))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v) || 'E-mail must be valid',
      ],
    };
  },
  methods: {
    ...mapActions(['emptyCart', 'sendReceipt']),
    submit() {
      if (this.isPhoneValid || this.isEmailValid) {
        var data = {
          cartHash: this.getCartHash,
          session: "SES" + Cookie.get('userToken') 
        } 
        if (this.isEmailValid) {
          data.email = this.email
        }
        if (this.isPhoneValid ) {
          data.phone = this.phone
        }
        // send to backend for digital receipt
        this.sendReceipt(data)
        .then((result) => { // no backend errors thrown
          this.$dbg_console_log(result)
          if(result.success==1) {
            this.$router.push('/');
          }
        })
        .catch(error => {
          this.show_popup = true
          this.popupHeader =  "Internal Server Error"
          this.popupText = "Something went wrong"
          this.$dbg_console_log(error)
        })
      }
    }
  },
}
</script>