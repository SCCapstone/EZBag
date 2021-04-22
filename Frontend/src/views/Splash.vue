<template>
  <v-container>
    <h1
      class='mt-2 font-weight-regular'>
      What is EZBag?
    </h1>
    <v-card
      class='mt-4'
      outlined>
      <v-card-title
        class='font-weight-regular'>
        TL;DR? Watch our video
      </v-card-title>
      <silent-box 
        :gallery="youtube_video"
        class='text-center'>
      </silent-box>
      <v-card-text>
        EZBag is a free point-of-sale (POS) service that reduces checkout
        expenses and enables a fast, touchless customer experience.  
      </v-card-text>
    </v-card>
    <h1
      class='mt-4 font-weight-regular'>
      Why EZBag?
    </h1>
    <v-row>
      <v-col>
      <v-card
        class='mt-4'
        outlined>
        <v-card-title
          class='justify-center font-weight-regular'>
          Your customers will thank you.
        </v-card-title>
        <v-carousel
          hide-delimiters
          show-arrows-on-hover>
          <v-carousel-item
            v-for="(item,i) in customer_gallery"
            :key="i"
            :src="item.src"
            background-size: contain>
          </v-carousel-item>
        </v-carousel>
        <v-card-text>
          Shopping with EZBag is fast and convenient. No downloads, no pesky
          notifications - just point, scan, and go.
        </v-card-text>
      </v-card>
      </v-col>
      <v-col>
        <v-card
          class='mt-4'
          outlined>
          <v-card-title
            class='justify-center font-weight-regular'>
            Your employees will thank you.
          </v-card-title>
          <v-carousel
            hide-delimiters
            show-arrows-on-hover>
            <v-carousel-item
              v-for="(item,i) in merchant_gallery"
              :key="i"
              :src="item.src"
              class="fill-height bottom-gradient"
              background-size: contain>
            </v-carousel-item>
          </v-carousel>
          <v-card-text>
            Verifying purchases is fast and easy. No more mindlessly scanning
            products while the line grows longer.
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
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
