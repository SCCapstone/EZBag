<template>
  <v-container>
    <h1
      class='text-center mt-2 font-weight-regular'>
      What is EZBag?
    </h1>
    <div
      class='text-center text-subtitle-1'>
      EZBag is a free point-of-sale (POS) service that reduces checkout
      expenses and enables a fast, touchless customer experience.  
    </div>
    <h1
      class='text-center mt-2 font-weight-regular pb-0'>
      Why EZBag?
    </h1>
    <div
      class='text-center font-italic font-weight-light mt-2'>
      your customers will thank you
    </div>
    <div
      class='text-center text-subtitle-1 mt-2'>
      Shopping with EZBag is fast and convenient. No downloads, no pesky
      notifications - just point, scan, and go.
    </div>
    <div
      class='text-center font-italic font-weight-light mt-4'>
      your employees will thank you
    </div>
    <div
      class='text-center text-subtitle-1 mt-2'>
      Verifying purchases is fast and easy. No more mindlessly scanning
      products while the line grows longer -  
      Covid-19 has demonstrated a need to reduce physical interaction.
    </div>
    <div
      class='text-center font-italic font-weight-light mt-4'>
      your wallet will thank you
    </div>
    <div
      class='text-center text-subtitle-1 mt-2'>
      Did we mention it's free?
    </div>
    <div class='mt-6'>
      <img
        src="https://raw.githubusercontent.com/wiki/SCCapstone/EZBag/images/splash/customer/scanner.png"
        alt="Receipt"
        style="width: 30%; height: 30%"
      />
      <img
        src="https://raw.githubusercontent.com/wiki/SCCapstone/EZBag/images/splash/customer/cart.png"
        alt="Receipt"
        style="width: 30%; height: 30%"
      />
      <img
        src="https://raw.githubusercontent.com/wiki/SCCapstone/EZBag/images/splash/customer/receipt.png"
        alt="Receipt"
        style="width: 30%; height: 30%"
      />
    </div>

    <div>
      <img
        src="https://raw.githubusercontent.com/wiki/SCCapstone/EZBag/images/splash/merchant/verification.png"
        alt="Receipt"
        style="width: 30%; height: 30%"
      />
      <img
        src="https://raw.githubusercontent.com/wiki/SCCapstone/EZBag/images/splash/merchant/sales.png"
        alt="Receipt"
        style="width: 30%; height: 30%"
      />
      <img
        src="https://raw.githubusercontent.com/wiki/SCCapstone/EZBag/images/splash/merchant/edit-product.png"
        alt="Receipt"
        style="width: 30%; height: 30%"
      />
      <img
        src="https://raw.githubusercontent.com/wiki/SCCapstone/EZBag/images/splash/merchant/add-product.png"
        alt="Receipt"
        style="width: 30%; height: 30%"
      />
    </div>
  </v-container>
</template>

<script>
export default {
  name: "Splash",
  components: {},
  data() {
    return {
      absolute: true,
      opacity: 1,
      overlay: false,
    }
  }
};
</script>

<style>
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