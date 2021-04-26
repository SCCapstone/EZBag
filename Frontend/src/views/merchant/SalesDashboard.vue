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
      HOD: ["11 PM","12 AM","1 AM","2 AM","3 AM","4 AM","5 AM","6 AM","7 AM","8 AM","9 AM","10 AM","11 AM",
            "12 PM","1 PM","2 PM","3 PM","4 PM","5 PM","6 PM","7 PM","8 PM","9 PM","10 PM",
      ],
      DOW: ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],
      DOM: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
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
      selected: "Last 24 Hours",
      interval: [
        "Last 24 Hours",
        "Last 7 Days",
        "Last 365 Days",
      ],
      selected2: "Net Profit",
      interval2: ["Net Profit", "Volume"],
      currentBarcode: "",
      productName: "",
      currentProduct: null,
    };
  },
  created() {
    this.fetchNewCarts(1)
  },
  methods: {
    ...mapActions(["fetchCartsInterval"]),
    fetchNewCarts(days){
      this.fetchCartsInterval({businessID:this.$route.params.id, days:days, barcode:this.currentBarcode})
      .then((result) => { // no backend errors thrown
      this.$dbg_console_log(result)
      if(result.success==1) {
        var carts = this.cleanData(result.carts, this.selected2=="Volume")
        if(this.selected=="Last 24 Hours"){
          this.setDataDay(carts, this.selected2 + " " + this.selected)
        }else if(this.selected=="Last 7 Days"){
          this.setDataWeek(carts, this.selected2 + " " + this.selected)
        }else{
          this.setDataMonth(carts, this.selected2 + " " + this.selected)
        }
      } else {
          this.$dbg_console_log("Failed to fetch carts")
      }
      }).catch(error => {
          this.$dbg_console_log(error)
      })
    },
    cleanData(data, isVolume){
      var retData = []
      for(var i=0; i<data.length; i++)
      {
        var time = new Date(parseInt(data[i].time.$numberLong));
        var volume = data[i].barcodes.length
        if(isVolume){
          retData[i] = {"time":time, "data":volume}
        }else{
          retData[i] = {"time":time, "data":volume*parseFloat(data[i].total)}
        }
      }
      console.log(retData)
      return retData
    },
    setDataDay(data, title){
      var xAxis = []
      var yAxis = []
      var currentDate = new Date(Date.now())
      for(var i=0; i<24; i++){
        xAxis[i] = this.HOD[currentDate.getHours()]
        yAxis[i] = 0
        currentDate.setHours(currentDate.getHours() - 1);
      }
      xAxis = xAxis.reverse()
      for(i=0;i<data.length; i++){
        var time = data[i].time
        time.setHours(time.getHours() - 1)
        var day = this.HOD[time.getHours()]
        var key = xAxis.indexOf(day)
        yAxis[key] = yAxis[key] + data[i].data
      }
      this.xAxisLabels = xAxis
      this.dataset = [
            {
              label: "",
              data: yAxis,
            },
          ]
      this.option.title.text = title

    },
    setDataWeek(data, title){
      var xAxis = []
      var yAxis = []
      var currentDate = new Date(Date.now())
      for(var i=0; i<7; i++){
        xAxis[i] = this.DOW[currentDate.getDay()]
        yAxis[i] = 0
        currentDate.setDate(currentDate.getDate() - 1);
      }
      xAxis = xAxis.reverse()
      for(i=0;i<data.length; i++){
        var day = this.DOW[data[i].time.getDay()]
        var key = xAxis.indexOf(day)
        yAxis[key] = yAxis[key] + data[i].data
      }
      this.xAxisLabels = xAxis
      this.dataset = [
            {
              label: "",
              data: yAxis,
            },
          ]
      this.option.title.text = title

    },
    setDataMonth(data, title){
      var xAxis = []
      var yAxis = []
      var currentDate = new Date(Date.now())
      for(var i=0; i<12; i++){
        xAxis[i] = this.DOM[currentDate.getMonth()]
        yAxis[i] = 0
        currentDate.setMonth(currentDate.getMonth() - 1);
      }
      xAxis = xAxis.reverse()
      for(i=0;i<data.length; i++){//asf
        var month = this.DOM[data[i].time.getMonth()]
        var key = xAxis.indexOf(month)
        yAxis[key] = yAxis[key] + data[i].data
      }
      console.log(yAxis)
      this.xAxisLabels = xAxis
      this.dataset = [
            {
              label: "",
              data: yAxis,
            },
          ]
      this.option.title.text = title

    },
    returnToAllSales() {
      this.showingProductSales = false;
      this.currentBarcode = ""
      this.getCarts();
    },
    getProductSalesInfo(barcode) {
      this.showingProductSales = true;
      this.currentBarcode = barcode
      this.getCarts()
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
    getCarts() {
      if(this.selected=="Last 24 Hours"){
        this.fetchNewCarts(1)
      }else if(this.selected=="Last 7 Days"){
        this.fetchNewCarts(7)
      }else{
        this.fetchNewCarts(365)
      }
    },
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
