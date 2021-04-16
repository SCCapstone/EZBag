<template>
  <v-container>
    <ScanSearchBar v-on:showproduct="getProductSalesInfo" ></ScanSearchBar>
    <v-card class="sales">
      <v-card-title class="justify-center">Total Sales Over Time</v-card-title>
      <v-card-actions>
        <v-select
          @change="getSelected($event)"
          :items="interval"
          v-model="selected"
          class="interval"
        ></v-select>
        <v-select
          @change="getSelected($event)"
          :items="interval2"
          v-model="selected2"
          class="interval"
        ></v-select>
      </v-card-actions>

      <SalesChart
        :width="500"
        :height="400"
        :labels="['Jan', 'Feb', 'Mar', 'Apr', 'May']"
        :datasets="dataset"
      ></SalesChart>

    </v-card>
    
  </v-container>
</template>

<script>
import {mapActions} from 'vuex';
import SalesChart from '@/components/merchant/SalesChart.vue'
import ScanSearchBar from '@/components/customer/scan/ScanSearchBar'

export default {
  name: "HelloWorld",

  components: {
    ScanSearchBar,
    SalesChart,
  },

  data() {
    return {
      dataset: [
        {
          label: '2018 Sales',
          data: [300, 700, 450, 750, 450]
        }
      ], 
      selected: 'Last 24 hours',
      interval: ['Last 24 hours', 'Last 7 days', 'Last 30 days', 'Last 365 days'],
      selected2: 'Net Profit',
      interval2: ['Net Profit', 'Transactions'],
      setofdatasets: [
        [ 
          [
            {
              label: 'Transactions last 24 hours',
              data: [1, 2, 3, 4, 5]
            }
          ],
          [
            {
              label: 'Net Profit last 24 hours',
              data: [1, 2, 3, 4, 5]
            }
          ]
        ],
        [
          [
            {
              label: 'Transactions last 7 days',
              data: [5,4,3,2,1]
            }
          ],
          [
            {
              label: 'Net Profit last 7 days',
              data: [5,4,3,2,1]
            }
          ]
        ],
        [
          [
            {
              label: 'Transactions last 30 days',
              data: [1, 2, 3, 2, 1]
            }
          ],
          [
            {
              label: 'Net Profit last 30 days',
              data: [1, 2, 3, 2, 1]
            }
          ],
        ],
        [
          [
            {
              label: 'Transactions last 365 days',
              data: [3,2,1,2,3]
            }
          ],
          [
            {
              label: 'Net Profit last 365 days',
              data: [3,2,1,2,3]
            }
          ],
        ],
      ]

    };
  },
  methods: {
    ...mapActions(["fetchCartsInterval"]),
    roundHours(date){
      date.setHours(date.getHours() + Math.round(date.getMinutes()/60));
      date.setMinutes(0, 0, 0); // Resets also seconds and milliseconds
      return date
    },
    updateChart(updateData, xaxis){
      this.$refs.barChart.updateSeries([{
        data: updateData,
      }], false, true);
      this.chartOptions = {...this.chartOptions, ...{
        xaxis: {
            categories: xaxis
        }
      }};
      this.$refs.barChart.updateOptions(
        this.chartOptions, true, true, true)
    },
    getSelected(value) {
      // can ignore value passed in because selected and selected2 updated
      console.log(value)
      if (this.selected === "Last 24 hours") {
        if (this.selected2 === "Transactions") {
          this.dataset = this.setofdatasets[0][0]
        } else {
          this.dataset = this.setofdatasets[0][1]
        }  
      } else if (this.selected === "Last 7 days") {
        if (this.selected2 === "Transactions") {
          this.dataset = this.setofdatasets[1][0]
        } else {
          this.dataset = this.setofdatasets[1][1]
        }  
      } else if (this.selected === "Last 30 days") {
        if (this.selected2 === "Transactions") {
          this.dataset = this.setofdatasets[2][0]
        } else {
          this.dataset = this.setofdatasets[2][1]
        }  
      } else { // last 365 days
        if (this.selected2 === "Transactions") {
          this.dataset = this.setofdatasets[3][0]
        } else {
          this.dataset = this.setofdatasets[3][1]
        }  
      }
    },
    getProductSalesInfo(barcode) {
      // TODO: get product sales info for given barcode and display it on chart
      var loadProductSalesInfo = {businessID:this.$route.params.id, barcode:barcode}
      console.log("Loading sales information for: ")
      console.log(loadProductSalesInfo)
    }
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

  },
  mounted(){
    this.getSelected("Last 24 hours")
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
