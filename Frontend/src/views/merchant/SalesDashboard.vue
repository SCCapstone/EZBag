<template>
  <v-container>
    <v-card class="sales">
      <v-card-title class="justify-center">Total Sales Over Time</v-card-title>
      <v-card-actions>
        <v-select
          :items="interval"
          v-model="selected"
          class="interval"
          dense
        ></v-select>
      </v-card-actions>
      <v-card-text>
        <div class="week" v-if="selected === 'Weekly'">
          <apexcharts
            type="bar"
            :options="chartOptions"
            :series="weekData"
          ></apexcharts>
        </div>
        <div class="month" v-if="selected === 'Monthly'">
          <apexcharts
            type="bar"
            :options="monthOptions"
            :series="monthData"
          ></apexcharts>
        </div>
        <div class="day" v-if="selected === 'Daily'">
          <apexcharts
            type="bar"
            :options="dayOptions"
            :series="dayData"
          ></apexcharts>
        </div>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import VueApexCharts from "vue-apexcharts";

export default {
  name: "HelloWorld",

  components: {
    apexcharts: VueApexCharts,
  },

  data: function () {
    return {
      selected: 'Weekly',
      interval: ['Daily', 'Weekly', 'Monthly'],
      chartOptions: {
        chart: {
          id: "basic-bar",
        },
        xaxis: {
          categories: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
        },
      },
      monthOptions: {
        chart: {
          id: "basic-bar",
        },
        xaxis: {
          categories: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        },
      },
      dayOptions: {
        chart: {
          id: "basic-bar",
        },
        xaxis: {
          categories: ['6am', '8am', '10am', '12pm', '2pm', '4pm', '6pm', '8pm', '10pm'],
        },
      },
      weekData: [
        {
          name: "weekly",
          data: [200, 450, 325, 350, 275, 250, 510],
        },
      ],
      dayData: [
        {
          name: "daily",
          data: [40, 20, 30, 35, 50, 60, 25, 75, 54],
        },
      ],
      monthData: [
        {
          name: "month",
          data: [4000, 3750, 4850, 3900, 4100, 4010, 3914, 2980, 4890, 5220, 5912, 4511],
        }
      ],
    };
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
