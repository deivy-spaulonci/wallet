<script setup lang="ts">
import { Bar } from 'vue-chartjs'
import { onMounted, ref } from 'vue';
import { despesaService } from '@/features/Despesa/services/despesa.service.ts';
import { useToast } from "primevue/usetoast";
import { chartView } from '@/components/shared/ChartView';

const toast = useToast();
const { chartData, chartOptions, legendas, valores } = chartView('Total por ano');
const ano = ref('2026');
const totalAno = ref(0);

async function carregar() {
  try {
    let param = { ano: ano.value }
    const response = await despesaService.getTotalPorMesDespesa(param);
    let dados = Array.isArray(response) ? response : [];
    legendas.value = dados.map(item => item.mes);
    valores.value = dados.map(item => item.total)
    totalAno.value = dados.reduce((soma, item) => soma + item.total, 0)
  } catch {
    toast.add({ severity: 'error', summary: 'Erro!', detail: 'Erro ao consultar gráfico.', life: 3000 });
  }
}

onMounted(() => {
  carregar()
})
</script>

<template>
  <div class="flex flex-row w-1/2 gap-5">
    <Select v-model="ano" :options="[2026, 2025, 2024, 2023, 2022, 2021, 2020, 2019, 2018, 2017, 2016, 2015, 2014, 2013, 2012, 2011]"
      scrollHeight="30rem" placeholder="Selecione um ano" class="w-40" @change="carregar" />
    <span class="italic font-bold text-green-400"> Total : {{ totalAno.toLocaleString('pt-BR', {
      style:
        'currency', currency: 'BRL'
    }) }} </span>
  </div>
  <div class="h-180">
    <Bar :data="chartData" :options="chartOptions" />
  </div>
</template>

<style></style>
