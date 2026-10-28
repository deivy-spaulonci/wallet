<script lang="ts" setup>
import { useToast } from 'primevue';
import { onMounted } from 'vue';
import { contaService } from '../../services/conta.service';
import { Pie } from 'vue-chartjs';
import { chartView } from '@/components/shared/ChartView';

const toast = useToast();

const { chartData, chartOptions, legendas, valores, cores } = chartView('Total por status');

async function carregar() {
  try {
    const response = await contaService.getTotalPorsStatus();
    let dados = Array.isArray(response) ? response : [];
    legendas.value = dados.map(item => item.status);
    valores.value = dados.map(item => item.total)
    cores.value = ["#00C950","#2B7FFF","#FB2C36","#F0B100"]
  } catch {
    toast.add({ severity: 'error', summary: 'Erro!', detail: 'Erro ao consultar gráfico.', life: 3000 });
  }
}

onMounted(() => {
  carregar()
})
</script>

<template>
  <div class="h-190">
    <Pie :data="chartData" :options="chartOptions" />
  </div>
</template>
