<template>
  <div id="backgroundCard">
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
    <div class="centerText" id="headerText">
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
      class="ovalButton button text"
      id="confirmButton">
          Confirm
    </button>
  </div>
</template>

<script>

// https://vuex.vuejs.org/guide/actions.html#dispatching-actions-in-components
import { mapGetters, mapActions } from 'vuex';
import Cookie from 'js-cookie'
export default {
  name:"Receipt",
  computed: mapGetters(['getCartHash']),
  data() {
    return {
      popupHeader: "Internal Error",
      popupText: "Something went wrong",
      show_popup: false,
    };
  },
  methods: {
    ...mapActions(['emptyCart', 'sendReceipt']),
    mobiledown: function(e) {
      e.target.classList.add("buttonActive")
    },
    mobileup: function(e) {
      this.clicked()
      e.target.classList.remove("buttonActive")
    },
    mousedown: function(e) {
      // Need to check if event was from mobile or desktop (event fires for mobile, but not correctly)
      if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
        return // we are on mobile. return to avoid calling method twice
      }
      e.target.classList.add("buttonActive")
    },
    mouseup: function(e) {
      // Need to check if event was from mobile or desktop (event fires for mobile, but not correctly)
      if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
        return // we are on mobile. return to avoid calling method twice
      }
      this.clicked()
      e.target.classList.remove("buttonActive")
    },
    clicked() {
      // TODO: get data from input forms
      var email = document.getElementById("emailForm").value;
      var addEmail = false;
      var phone = document.getElementById("phoneForm").value;
      var addPhone = false;

      // simple checks on phone and email
      if (email !== "") {
        
        if (email.includes("@")) {
          console.log("proper email: ", email)
          addEmail = true;
        } else {
          // TODO: elegant correction message
          console.log("Email improperly formatted")
        }
      } else {
        console.log("email field empty")
      }

      if (phone !== "") {
        if (phone.length == 10) {
          console.log("proper phone: ", phone)
          addPhone = true;
        } else {
          // TODO: elegant correction message
          console.log("Phone number improper length")
        }
      } else {
        console.log("phone field empty")
      }

      var data = {
                "cartHash": this.getCartHash
              }
      if (addEmail) {
        data.email = email;
      }
      if (addPhone) {
        data.phone = phone;
      }

      data.session = "SES" + Cookie.get('userToken')
      console.log(data)

      if (addEmail || addPhone) {
        console.log("sending digital receipt to given contact")
        // send to backend for digital receipt
        //print(data)
        this.sendReceipt(data
        ).then((result) => { // no backend errors thrown
          this.$dbg_console_log(result)
          if(result.success==1) {
            this.$router.push('/');
          }else{
            this.show_popup = true
            this.popupHeader =  "Empty Fields"
            this.popupText = result.message
          }
        }).catch(error => {
          this.show_popup = true
          this.popupHeader =  "Internal Server Error"
          this.popupText = "Something went wrong"
          console.log(error)
        })
        /*
        jQuery.post(
          "/EZBagWebapp/webapi/info",
          data,
          function(data, status) {
            // handle json object return as string
            if (typeof(data) == "string")
              data = JSON.parse(data)
            if (status == "success" && data.status !== "failure") {
              console.log("Successfully sent digital receipt")
              ref.emptyCart()
              ref.$router.push('/');
              //alert("Sent digital receipt, starting new shopping session");
            } else {
              console.log("Failed to send digital receipt")
            }
          }
        );
        */
      } else {
        this.popupHeader =  "Empty Fields"
        this.popupText = "Email and phone number fields are empty!"
        this.show_popup = true
      }

  }

  },
}
</script>

<style scoped>

/*
  Fix for word-wrapping in title. To be fixed in Vuetify 3.
  https://github.com/vuetifyjs/vuetify/issues/9130#issuecomment-542534966
*/
#backgroundCard
{
  position: absolute;
  width: 100%;
  height: 100%;
  bottom: 0px;
  background-color: rgb(230,230,240);
}

.inputForm
{
    position: relative;
    width: 80%;
    height: 50px;
    left: 10%;
    top: 20px;
    margin-bottom: 30px;
    border-radius: 70px;
    outline: none;
    font-size:26px;
    text-align: center;
    color: black;
    background-color: rgb(245,245,245);;
}

.horizontalBar
{
  width: 100%;
  height: 1px;
  background-color: black;
}

#cartNumber
{
  margin-top: 12px;
  font-weight: 120;
  font-size: 72px;
}

#headerText
{
  margin-top: 12px;
  font-size: 18px;
}

.centerText
{
  text-align: center;
}

.v-card__text, .v-card__title {
  word-break: normal;
}

#confirmButton
{
  position: absolute;
  bottom: 40px;
}

.button {
    -webkit-box-shadow: 0px 11px 12px 0px rgba(0,0,0,0.34);
    -moz-box-shadow: 0px 11px 12px 0px rgba(0,0,0,0.34);
    box-shadow: 0px 11px 12px 0px rgba(0,0,0,0.34);
}

.ovalButton {
    width: 80%;
    left: 10%;
    font-size: 36px;
    font-weight: 300;
    height: 50px;
    background-color: #fafafa;
    border: none;
    color: black;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    border-radius: 70px;
}

.buttonActive
{
    -webkit-box-shadow: inset 0px 11px 12px 0px rgba(0,0,0,0.34);
    -moz-box-shadow: inset 0px 11px 12px 0px rgba(0,0,0,0.34);
    box-shadow: inset 0px 11px 12px 0px rgba(0,0,0,0.34);
}

.button:focus {
    outline: none;
}

</style>