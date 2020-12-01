<template>
  <div id="backgroundCard">
    <div class="centerText" id="headerText">
      Checkout successful<br>
      Your cart number is:
    </div>
    <div class="centerText" id="cartNumber">
      <!-- {{cartNum="#45"}} --> 
    </div>
    <div class="horizontalBar"></div>
    <div class="centerText" id="headerText">
      Enter your email and or phone number<br>
      to receive a digital receipt
    </div>
    <input class="inputForm" v-model="message" placeholder="email">
    <input class="inputForm" v-model="message" placeholder="phone number">
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

function clicked()
{
  console.log("Clicked")
}

// https://vuex.vuejs.org/guide/actions.html#dispatching-actions-in-components
import { mapGetters, mapActions } from 'vuex';

export default {
  name:"Receipt",
  methods: {
    mobiledown: function(e) {
      e.target.classList.add("buttonActive")
    },
    mobileup: function(e) {
      clicked()
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
      clicked()
      e.target.classList.remove("buttonActive")
    },
    ...mapActions([])
  },
  computed: mapGetters([])
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