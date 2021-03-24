<template>
  <v-container>
    <v-card class='sales'>
      <v-card-actions>
        <v-select
          :items="interval"
          v-model="selected"
          item-value="val"
          @change="changeInterval($event)"
          label="Time interval"
          class='interval'
          dense
        ></v-select>
      </v-card-actions>
      <v-card-text>
        <bar-chart v-if="selected === 'Monthly'"
          :chartData="datasets.monthlyTotals"
          :options="chartOptions"
          :labels="labels.monthInterval"
          label="Monthly Data"
        ></bar-chart>
        <bar-chart v-if="selected === 'Weekly'"
          :chartData="datasets.weeklyTotals"
          :options="chartOptions"
          :labels="labels.weekInterval"
          label="Weekly Data"
        ></bar-chart>
        <bar-chart v-if="selected === 'Daily'"
          :chartData="datasets.dailyTotals"
          :options="chartOptions"
          :labels="labels.dayInterval"
          label="Daily Data"
        ></bar-chart>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import BarChart from "./barChart1.vue";

export default {
  name: 'SalesDashboard',
  //datasets,
  //options,
  components: {
    BarChart
  },

  data() {
    return {
      selected: "",
      interval: ['Monthly', 'Weekly', 'Daily'],
      labels: {
        monthInterval: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        weekInterval: ['Sun.', 'Mon.', 'Tues.', 'Wed.', 'Thurs.', 'Fri.', 'Sat.'],
        dayInterval: ['6am', '8am', '10am', '12pm', '2pm', '4pm', '6pm', '8pm', '10pm'],
      },
      datasets: {
        monthlyTotals: [4000, 3750, 4850, 3900, 4100, 4010, 3914, 2980, 4890, 5220, 5912, 4511],
        weeklyTotals: [200, 450, 325, 350, 275, 250, 510, 0, 0, 0, 0, 0],
        dailyTotals: [40, 20, 30, 35, 50, 60, 25, 75, 54, 32, 70, 65],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            xAxes: [{
                barPercentage: 0.4
            }],
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        },
        legend: {
            display: false
        },
        title: {
          display: true,
          text: 'Total Sales Over Time'
        },
      },
    };
  },
  method: {
    changeInterval: function changeInterval(event) {
      this.selected = event.value;
    }
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .sales {
    padding:5px;
  }

  .v-select {
    text-size-adjust: 40%;
  }

  .v-card__text {
    padding: 0;
  }

  .v-card__actions {
    padding-bottom: 0;
    padding-top: 0;
    width: 35%;
  }
</style>
