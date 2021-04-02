<template>
  <v-container>
    <v-card class="sales">
      <v-card-title class="justify-center">Total Sales Over Time</v-card-title>
      <v-card-actions>
        <v-select
          @change="getSelected($event)"
          :items="interval"
          v-model="selected"
          class="interval"
          dense
        ></v-select>
      </v-card-actions>
      <v-card-text>
        <apexcharts
          type="bar"
          ref="barChart"
          :options="barChartOptions"
          :series="barChartData"
        ></apexcharts>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import VueApexCharts from "vue-apexcharts";
import {mapActions} from 'vuex';

export default {
  name: "HelloWorld",

  components: {
    apexcharts: VueApexCharts,
  },

  data() {
    return {
      selected: 'Weekly',
      currentSelection: "Weekly",
      interval: ['Daily', 'Weekly', 'Monthly'],
      barChartOptions: {
        chart: {
          id: "basic-bar",
        },
        xaxis: {
          categories: ['12am', '3am', '6am', '9am', "12pm", '3pm', '6pm', '9pm'],
        },
      },

      barChartOptions2: {
        chart: {
          id: "basic-bar",
        },
        xaxis: {
          categories: ['10am', '3am', '6am', '9am', "12pm", '3pm', '6pm', '9pm'],
        },
      },
      barChartData: [
        {
          name: "daily",
          data: [200, 450, 325, 350, 275, 250, 510, 0],
        },
      ],
      dayData: [200, 450, 325, 350, 275, 250, 510, 0],
      dayAxis: ['12am', '3am', '6am', '9am', "12pm", '3pm', '6pm', '9pm'],
      weekAxis: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', "Thursday", 'Friday', 'Saturday'],
      monthAxis: ['January', 'Febraury', 'March', 'April', "May", 'June', 'July', 'August', 'September', 'October', 'November', 'Decembet'],
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
      
      this.fetchCartsInterval({businessID:this.$route.params.id, interval:value})
      .then((result) => { // no backend errors thrown
      this.$dbg_console_log(result)
      var plotData = {}
      if(result.success==1) {
          for(var i=0; i<result.carts.length; i++)
          {  
            var date = new Date(parseInt(result.carts[i].time.$numberLong));
            var timeKey
            if(value=="Daily"){
              date = this.roundHours(date)
              var hours = date.getHours()
              var time = "am"
              hours = Math.round((hours-2)/3)*3
              if(date.getHours()>12){
                time = "pm"
              }
              timeKey = hours + time
              if(timeKey in plotData){
                plotData[timeKey] = plotData[timeKey] + 1
              }else{
                plotData[timeKey] = 1
              }
            }else if(value=="Weekly"){
              timeKey = this.weekAxis[date.getDay()]
              if(timeKey in plotData){
                plotData[timeKey] = plotData[timeKey] + 1
              }else{
                plotData[timeKey] = 1
              }
            }else if(value=="Monthly"){
              timeKey = this.monthAxis[date.getMonth()]
              if(timeKey in plotData){
                plotData[timeKey] = plotData[timeKey] + 1
              }else{
                plotData[timeKey] = 1
              }
            }
          }
          var val
          if(value=="Daily"){
            var dayData = []
            for(i=0; i<this.dayAxis.length; i++)
            {
              dayData.push(0)
            } 
            for(i=0; i<this.dayAxis.length; i++)
            {
              val = this.dayAxis[i]
              if(val in plotData)
              {
                dayData[i] = plotData[val]
              }
            }
            this.updateChart(dayData, this.dayAxis)
          }else if(value=="Weekly"){
            var weekData = []
            for(i=0; i<this.weekAxis.length; i++)
            {
              weekData.push(0)
            } 
            for(i=0; i<this.weekAxis.length; i++)
            {
              val = this.weekAxis[i]
              if(val in plotData)
              {
                weekData[i] = plotData[val]
              }
            }
            this.updateChart(weekData, this.weekAxis)
          }else if(value=="Monthly"){
            var monthData = []
            for(i=0; i<this.monthAxis.length; i++)
            {
              monthData.push(0)
            } 
            for(i=0; i<this.monthAxis.length; i++)
            {
              val = this.monthAxis[i]
              if(val in plotData)
              {
                monthData[i] = plotData[val]
              }
            }
            this.updateChart(monthData, this.monthAxis)
          }
      } else {
          this.$dbg_console_log("Failed to fetch carts")
      }
      }).catch(error => {
          this.$dbg_console_log(error)
      })
      console.log(value)
    },
  }
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
