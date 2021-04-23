<template>
  <v-container>
    <ScanSearchBar v-on:showproduct="getProductSalesInfo"></ScanSearchBar>
    <v-card class="sales">
      <!-- <v-card-title class="justify-center">Total Sales Over Time</v-card-title> -->
      <v-card-actions>
        <v-select
          @change="getCarts()"
          :items="interval"
          v-model="selected"
          class="interval"
        ></v-select>
        <v-select
          @change="getCarts()"
          :items="interval2"
          v-model="selected2"
          class="interval"
        ></v-select>
        <v-btn v-show="showingProductSales" v-on:click="returnToAllSales">
          Reset
        </v-btn>
      </v-card-actions>
      <SalesChart
        :width="500"
        :height="400"
        :labels="xAxisLabels"
        :datasets="dataset"
        :options="option"
        :key="option.title.text"
      ></SalesChart>
    </v-card>
  </v-container>
</template>

<script>
import { mapActions } from "vuex";
import SalesChart from "@/components/merchant/SalesChart.vue";
import ScanSearchBar from "@/components/customer/scan/ScanSearchBar";

export default {
  name: "HelloWorld",

  components: {
    ScanSearchBar,
    SalesChart,
  },

  data() {
    return {
      showingProductSales: false,
      xAxisLabels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
      dataset: [
        {
          label: "2018 Sales",
          data: [300, 700, 450, 750, 450],
        },
      ],
      option: {
        title: {
          display: true,
          text: "Total Sales Over Time",
          fontSize: 20,
        },
        legend: {
          position: "bottom",
          display: false,
        },
        scales: {
            yAxes: [
              {
                ticks: {
                  beginAtZero: true,
                },
              }
            ]
          },
      },
      selected: "Last 24 hours",
      interval: [
        "Last 24 hours",
        "Last 7 days",
        "Last 30 days",
        "Last 365 days",
      ],
      selected2: "Net Profit",
      interval2: ["Net Profit", "Transactions"],
      allSalesDatasets: [
        [
          [
            {
              label: "Transactions last 24 hours",
              data: [1, 2, 3, 4, 5],
            },
          ],
          [
            {
              label: "Net Profit last 24 hours",
              data: [1, 2, 3, 4, 5],
            },
          ],
        ],
        [
          [
            {
              label: "Transactions last 7 days",
              data: [5, 4, 3, 2, 1],
            },
          ],
          [
            {
              label: "Net Profit last 7 days",
              data: [5, 4, 3, 2, 1],
            },
          ],
        ],
        [
          [
            {
              label: "Transactions last 30 days",
              data: [1, 2, 3, 2, 1],
            },
          ],
          [
            {
              label: "Net Profit last 30 days",
              data: [1, 2, 3, 2, 1],
            },
          ],
        ],
        [
          [
            {
              label: "Transactions last 365 days",
              data: [3, 2, 1, 2, 3],
            },
          ],
          [
            {
              label: "Net Profit last 365 days",
              data: [3, 2, 1, 2, 3],
            },
          ],
        ],
      ],
      productSalesDatasets: [
        [
          [
            {
              label: "Transactions last 24 hours",
              data: [1, 1, 1, 1, 1],
            },
          ],
          [
            {
              label: "Net Profit last 24 hours",
              data: [1, 1, 1, 1, 1],
            },
          ],
        ],
        [
          [
            {
              label: "Transactions last 7 days",
              data: [1, 1, 1, 1, 1],
            },
          ],
          [
            {
              label: "Net Profit last 7 days",
              data: [1, 1, 1, 1, 1],
            },
          ],
        ],
        [
          [
            {
              label: "Transactions last 30 days",
              data: [1, 1, 1, 1, 1],
            },
          ],
          [
            {
              label: "Net Profit last 30 days",
              data: [1, 1, 1, 1, 1],
            },
          ],
        ],
        [
          [
            {
              label: "Transactions last 365 days",
              data: [1, 1, 1, 1, 1],
            },
          ],
          [
            {
              label: "Net Profit last 365 days",
              data: [1, 1, 1, 1, 1],
            },
          ],
        ],
      ],
      productName: "",
      currentProduct: null,
    };
  },
  methods: {
    ...mapActions(["fetchCartsInterval"]),
    returnToAllSales() {
      this.showingProductSales = false;
      this.getCarts();
    },
    getProductSalesInfo(barcode) {
      // TODO: get product sales info for given barcode and display it on chart
      var loadProductSalesInfo = {
        businessID: this.$route.params.id,
        barcode: barcode,
      };

      console.log("Loading sales information for: ");
      console.log(loadProductSalesInfo);

      // TODO: compute productSalesDatasets
      // TODO: set productName
      this.showingProductSales = true;
      this.currentProduct = null;

      console.log("display new product: " + barcode);

      // get product name
      this.$store
        .dispatch("lookupProduct", {
          businessID: this.$route.params.id,
          barcode: barcode,
        })
        .then((result) => {
          console.log(result.product);
          this.currentProduct = result.product;
          if (this.currentProduct === null) {
            console.log("ERROR, PRODUCT LOOKUP FAILED");
            // TODO: handle this case and display error popup
          } else {
            this.productName = this.currentProduct.name;
            console.log("Getting carts for "+this.productName+"...");
            this.getCarts();
          }
        })
        .catch((error) => {
          console.log(error);
          // TODO: handle this case and display error popup
        });

        

    },
    //   this.fetchCartsInterval({businessID:this.$route.params.id, interval:value})
    //   .then((result) => { // no backend errors thrown
    //   this.$dbg_console_log(result)
    //   var plotData = {}
    //   if(result.success==1) {
    //       for(var i=0; i<result.carts.length; i++)
    //       {
    //         var date = new Date(parseInt(result.carts[i].time.$numberLong));
    //         var timeKey
    //         if(value=="Daily"){
    //           date = this.roundHours(date)
    //           var hours = date.getHours()
    //           var time = "am"
    //           hours = Math.floor(hours/3)*3
    //           if(date.getHours()>12){
    //             time = "pm"
    //             hours = hours - 12
    //             if(hours==0)
    //               hours=12
    //           }
    //           timeKey = hours + time
    //           if(timeKey in plotData){
    //             plotData[timeKey] = plotData[timeKey] + 1
    //           }else{
    //             plotData[timeKey] = 1
    //           }
    //         }else if(value=="Weekly"){
    //           timeKey = this.weekAxis[date.getDay()]
    //           if(timeKey in plotData){
    //             plotData[timeKey] = plotData[timeKey] + 1
    //           }else{
    //             plotData[timeKey] = 1
    //           }
    //         }else if(value=="Monthly"){
    //           timeKey = this.monthAxis[date.getMonth()]
    //           if(timeKey in plotData){
    //             plotData[timeKey] = plotData[timeKey] + 1
    //           }else{
    //             plotData[timeKey] = 1
    //           }
    //         }
    //       }
    //       var val
    //       if(value=="Daily"){
    //         var dayData = []
    //         for(i=0; i<this.dayAxis.length; i++)
    //         {
    //           dayData.push(0)
    //         }
    //         for(i=0; i<this.dayAxis.length; i++)
    //         {
    //           val = this.dayAxis[i]
    //           if(val in plotData)
    //           {
    //             dayData[i] = plotData[val]
    //           }
    //         }
    //         console.log(plotData)
    //         console.log(dayData)
    //         this.updateChart(dayData, this.dayAxis)
    //       }else if(value=="Weekly"){
    //         var weekData = []
    //         for(i=0; i<this.weekAxis.length; i++)
    //         {
    //           weekData.push(0)
    //         }
    //         for(i=0; i<this.weekAxis.length; i++)
    //         {
    //           val = this.weekAxis[i]
    //           if(val in plotData)
    //           {
    //             weekData[i] = plotData[val]
    //           }
    //         }
    //         this.updateChart(weekData, this.weekAxis)
    //       }else if(value=="Monthly"){
    //         var monthData = []
    //         for(i=0; i<this.monthAxis.length; i++)
    //         {
    //           monthData.push(0)
    //         }
    //         for(i=0; i<this.monthAxis.length; i++)
    //         {
    //           val = this.monthAxis[i]
    //           if(val in plotData)
    //           {
    //             monthData[i] = plotData[val]
    //           }
    //         }
    //         this.updateChart(monthData, this.monthAxis)
    //       }
    //   } else {
    //       this.$dbg_console_log("Failed to fetch carts")
    //   }
    //   }).catch(error => {
    //       this.$dbg_console_log(error)
    //   })
    //   console.log(value)
    // },
    getDaysFromSelected() {
      switch (this.selected) {
        case "Last 24 hours":
          return 1;
        case "Last 7 days":
          return 7;
        case "Last 30 days":
          return 30;
        case "Last 365 days":
          return 365;
        default:
          console.log("ERROR: COULD NOT FIND DAYS FOR SELECTED TIME");
          return 1;
      }
    },
    setChartTitleFromSelected() {
      var prefix = ""
      if (this.showingProductSales) {
        prefix += this.productName + " "
      }
      if (this.selected2 === "Transactions") {
        this.option.title.text = prefix + "Transactions Over Time"
      } else {
        this.option.title.text = prefix + "Sales Over Time"
      }
    },
    getAxisLabelFromSelected() {
      switch (this.selected) {
        case "Last 24 hours":
          return ["12 am", "3 am", "6 am", "9 am", "12 pm", "3 pm", "6 pm", "9 pm"]
        case "Last 7 days":
          return ["Sun", "Mon", "Tues", "Wed", "Thu", "Fri", "Sat"]
        case "Last 30 days":
          return ["0", "3", "6", "9", "12", "15", "18", "21", "24", "27", "30"]
        case "Last 365 days":
          return ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
        default:
          console.log("ERROR: COULD NOT FIND DAYS FOR SELECTED TIME");
          return ["12 am", "3 am", "6 am", "9 am", "12 pm", "3 pm", "6 pm", "9 pm"]
      }
    },
    setChartXAxisFromSelected() {
      // set chart axis  
      var xAxisLabel = this.getAxisLabelFromSelected()
      // TODO: rotate xAxisLabel list to match current time / date
      this.xAxisLabels = xAxisLabel
    },
    getCarts() {
      var days = this.getDaysFromSelected();
      if (this.showingProductSales) {
        console.log(
          "Getting " + this.productName + " carts for " + days + " days"
        );
        this.parseCarts(null); // TODO: 
      } else {
        console.log("Getting all sales carts for " + days + " days");
        this.$store
          .dispatch("getAllSalesData", {
            businessID: this.$route.params.id,
            days: days,
          })
          .then((result) => {
            // parse carts and generate plotable data
            this.parseCarts(result.carts);
          })
          .catch((error) => {
            console.log(error);
            this.parseCarts(null);
          });
      }
    },
    parseCarts(carts) {
      console.log(carts);
      // set chart title
      this.setChartTitleFromSelected();
      this.setChartXAxisFromSelected();
      // set chart data
      if (this.showingProductSales) {
        // parse carts w.r.t. product barcode
        console.log("TODO: parse carts for product data to plot");
        
      } else {
        console.log("Parsing all store sales carts");

        if (carts === null) {
          console.log("TODO: carts is null");
        } else if (carts.length === 0) {
          console.log("TODO: carts is length 0");
          var data = [0,1,0,1,2,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
          this.dataset = [
            {
              label: "this doesnt matter",
              data: data,
            },
          ]
        } else {
          console.log("TODO: parsing carts...");
          for (var i = 0; i < carts.length; i++) {
            console.log(i);
            // TODO: 
          }
        }
      }
    },
  },
  mounted() {
    // TODO: get all store sales and store in this.datasets
    console.log("Getting carts...");
    this.getCarts();
  },
};
</script>

<style scoped>
.sales {
  padding: 5px;
}

.v-card__title {
  padding: 0;
}

.v-select {
  text-size-adjust: 40%;
}

.v-card__actions {
  padding-bottom: 0;
  padding-top: 0;
  width: 35%;
}

.v-card__text {
  padding: 0;
}
</style>
