<script setup lang="ts">
import { Bar } from 'vue-chartjs'
import { onMounted } from 'vue';
import { contaService } from '@/features/Conta/services/conta.service.ts';
import { useToast } from "primevue/usetoast";
import { chartView } from '@/components/shared/ChartView';

const toast = useToast();

const { chartData, chartOptions, legendas, valores } = chartView('Total por tipo de conta');

async function carregar() {
  try {
    const response = await contaService.getTotalPorTipoConta();
    let dados = Array.isArray(response) ? response : [];
    legendas.value = dados.map(item => item.tipoConta);
    valores.value = dados.map(item => item.total)
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
    <Bar :data="chartData" :options="chartOptions" />
  </div>
</template>

<style></style>
