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
    <silent-box 
      :gallery="youtube_video"
      class='text-center'>
    </silent-box>
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
    <silent-box
      :gallery="customer_gallery"
      class='text-center'>
    </silent-box>
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
    <silent-box
      :gallery="merchant_gallery"
      class='text-center'>
    </silent-box>
    <div
      class='text-center font-italic font-weight-light mt-4'>
      your wallet will thank you
    </div>
    <div
      class='text-center text-subtitle-1 mt-2'>
      Did we mention it's free?
    </div>
  </v-container>
</template>

<script>
export default {
  name: "Splash",
  components: {},
  data: () => ({
    absolute: true,
    opacity: 1,
    overlay: false,
    youtube_video: [
      {
        src:'https://www.youtube.com/embed/KkPlkvzmflU',
        thumbnailWidth: '300px',
        controls: false,
      }
    ],
    customer_gallery: [
      {
        src:"https://raw.githubusercontent.com/wiki/SCCapstone/EZBag/images/splash/customer/scanner.png",
        alt:"Scanner",
        thumbnailWidth: '100px',
        desc:''
      },
      {
        src:"https://raw.githubusercontent.com/wiki/SCCapstone/EZBag/images/splash/customer/cart.png",
        alt:"Cart",
        thumbnailWidth: '100px',
      },
      {
        src:"https://raw.githubusercontent.com/wiki/SCCapstone/EZBag/images/splash/customer/receipt.png",
        alt:"Receipt",
        thumbnailWidth: '100px',
      },
    ],
    merchant_gallery: [
      {
        src:"https://raw.githubusercontent.com/wiki/SCCapstone/EZBag/images/splash/merchant/verification.png",
        alt:"Verification",
        thumbnailWidth: '100px',
        desc:''
      },
      {
        src:"https://raw.githubusercontent.com/wiki/SCCapstone/EZBag/images/splash/merchant/sales.png",
        alt:"Sales",
        thumbnailWidth: '100px',
      },
      {
        src:"https://raw.githubusercontent.com/wiki/SCCapstone/EZBag/images/splash/merchant/add-product.png",
        alt:"Add Products",
        thumbnailWidth: '100px',
      },
    ]
  })
};
</script>
