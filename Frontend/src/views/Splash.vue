<template>
  <div class="splash">
    <SplashNavigation></SplashNavigation>
    <div id="wrapper">
      <div class="center">
        <h1>Welcome to EZBag</h1>
      </div>
      <div class="center">
        <iframe
          width="560"
          height="315"
          src="https://www.youtube.com/embed/KkPlkvzmflU"
          title="YouTube video player"
          frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowfullscreen
        ></iframe>
      </div>
      <div>
        <br />
        <p>
          EZBag is a free scan-and-go web application. Businesses will be
          provided with QR codes for customers to access the EZBag web
          application. Users can scan any product’s barcode with their mobile
          camera as well as search for specific products and add them to their
          cart. Customers can then review their cart and checkout via the web
          application, and they will have the option of receiving their receipt
          via email and/or text.
        </p>
      </div>
      <div class="center">
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        Go to
      </div>
      <div class="center">
        <router-link to="/login"> Merchant Login</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import SplashNavigation from '@/components/SplashNavigation.vue'

export default {
  name: "Splash",
  components: {
    SplashNavigation,
  },
};
</script>

<style>
#wrapper {
  margin-top: 13%;
  margin-left: 10%;
  margin-right: 10%;
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