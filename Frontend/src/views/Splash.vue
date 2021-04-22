<template>
  <v-container>
    <h1
      class='text-center mt-2 font-weight-regular'>
      What is EZBag?
    </h1>
    <v-card class='mt-4'>
      <silent-box 
        :gallery="youtube_video"
        class='text-center'>
      </silent-box>
      <v-card-text
        class='text-center text-subtitle-1'>
        EZBag is a free point-of-sale (POS) service that reduces checkout
        expenses and enables a fast, touchless customer experience.  
      </v-card-text>
    </v-card>

    <h1
      class='text-center mt-4 font-weight-regular'>
      Why EZBag?
    </h1>
    <v-card class='mt-4'>
      <v-card-title
        class='font-weight-regular'>
        Your customers will thank you.
      </v-card-title>
      <silent-box
        :gallery="customer_gallery"
        class='text-center'>
      </silent-box>
      <v-card-text>
        Shopping with EZBag is fast and convenient. No downloads, no pesky
        notifications - just point, scan, and go.
      </v-card-text>
    </v-card>
    <v-card class='mt-4'>
      <v-card-title
        class='font-weight-regular'>
        Your employees will thank you.
      </v-card-title>
      <silent-box
        :gallery="merchant_gallery"
        class='text-center'>
      </silent-box>
      <v-card-text>
        Verifying purchases is fast and easy. No more mindlessly scanning
        products while the line grows longer.
      </v-card-text>
    </v-card>
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
        desc:'this is a test'
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
