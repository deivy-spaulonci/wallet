import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  ArcElement,
  PointElement,
  LineElement
} from "chart.js";

import { computed, ref } from "vue";

export function chartView(titulo: string) {
  const legendas = ref<any[]>([]);
  const valores = ref<any[]>([]);
  const cores = ref<any[]>(["#006400", "#32CD32"]);

  ChartJS.register(
    Title,
    Tooltip,
    Legend,
    BarElement,
    CategoryScale,
    LinearScale,
    ArcElement,
    PointElement,
    LineElement
  );

  const chartData = computed(() => ({
    labels: legendas.value,
    datasets: [
      {
        label: titulo,
        data: valores.value, //valores.value.map(item => item.total),
        backgroundColor: cores.value,
      },
    ],
  }));

  const chartOptions = {
    responsive: true,
    maintainAspectRatio: false,
  };

  return { chartData, chartOptions, legendas, valores, cores };
}
