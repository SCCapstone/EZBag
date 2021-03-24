<script>
import { Bar } from "vue-chartjs";

export default {
  name: 'sales-chart',
  extends: Bar,
  props: {
    label: {
      type: String
    },
    labels: {
      type: Array
    },
    chartData: {
      type: Array
    },
    options: {
      type: Object
    }
  },
  mounted() {
    // Store the created instance.
    const interval = this.labels.map(interval => interval);
    //console.log(interval)
    const totals = this.chartData.map(totals => totals);

    this.renderChart(
      {
        labels: interval,
        datasets: [
          {
            label: this.label,
            data: totals,
            backgroundColor: "#1976d2",
          }
        ],
      },
      this.options
    );
  }
}
</script>