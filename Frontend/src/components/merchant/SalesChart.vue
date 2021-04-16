<template>
  <canvas ref="myChart" :width="width" :height="height"></canvas>
</template>

<script>
import Chart from 'chart.js';

export default {
  name: 'SalesChart',
  props: ['width', 'height', 'labels', 'datasets', 'options'],
  mounted() {
    new Chart(this.$refs.myChart, {
      type: 'line',
      data: {
        labels: this.labels,
        datasets: this.datasets
      },
      options: this.options
    });
  },
  watch: {
    datasets: function() {
      console.log('Updating sales chart with new data')
      new Chart(this.$refs.myChart, {
        type: 'line',
        data: {
          labels: this.labels,
          datasets: this.datasets
        },
        options: this.options
      });
    }
  }
}
</script>