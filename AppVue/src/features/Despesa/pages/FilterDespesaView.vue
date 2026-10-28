<script setup lang="ts">
import { onMounted, ref } from 'vue';
import Search from '@primeicons/vue/search';
import Eraser from '@primeicons/vue/eraser';
import Times from '@primeicons/vue/times';
import { filterDespesaView } from './FilterDespesaView';

const emit = defineEmits(['filtrar'])
  
const { selectedTipoDespesa, selectedFornecedor, selectedFormaPgto, tipos, fornecedores, formas, dataInicial, 
        dataFinal, mesDate, opcPeriodo, carregarTipos, carregarFormas, search, limparFiltros, filtrar
    } = filterDespesaView(emit);

onMounted(() => {
  carregarTipos()
  carregarFormas()
});
</script>

<template>
  <div class="flex flex-col gap-2 p-4">
    <Divider type="dashed">
      <code class="uppercase text-xs">Filtros</code>
    </Divider>

    <Label>Tipo Despesa:</Label>
    <Select v-model="selectedTipoDespesa" :options="tipos" multiple checkmark optionLabel="nome" optionValue="value"
      placeholder="selecione uma despesa" class="w-full" scrollHeight="230" showClear />

    <Label>Fornecedor:</Label>
    <AutoComplete v-model="selectedFornecedor" :suggestions="fornecedores" @complete="search" optionLabel="nome"
      placeholder="" inputClass="w-full" showClear emptySearchMessage="nenhum fornecedor encontrado"
      emptySelectionMessage="nenhum fornecedor encontrado" scrollHeight="230" />

    <div class="flex flex-row gap-2 content-center items-center">
      <RadioButton v-model="opcPeriodo" inputId="opc-periodo" value="periodo" />
      <Label for="opc-periodo">Periodo:</Label>
    </div>
    <div class="flex flex-row w-full gap-2">
      <IconField>
        <InputText :disabled="opcPeriodo == 'mes'" v-model="dataInicial"
          v-mask="{ mask: '99/99/9999', slotChar: 'dd/mm/yyyy' }" placeholder="dd/mm/yyyy" class="w-35" />
        <InputIcon v-if="dataInicial" class="cursor-pointer" @click="dataInicial = ''">
          <Times />
        </InputIcon>
      </IconField>
      <span>até</span>
      <IconField>
        <InputText :disabled="opcPeriodo == 'mes'" v-model="dataFinal"
          v-mask="{ mask: '99/99/9999', slotChar: 'dd/mm/yyyy' }" placeholder="dd/mm/yyyy" class="w-35" />
        <InputIcon v-if="dataFinal" class="cursor-pointer" @click="dataFinal = ''">
          <Times />
        </InputIcon>
      </IconField>
    </div>

    <div class="flex flex-row gap-2 content-center items-center">
      <RadioButton v-model="opcPeriodo" inputId="opc-mes" value="mes" />
      <Label for="opc-mes">Por mês:</Label>
    </div>
    <DatePicker v-model="mesDate" :disabled="opcPeriodo == 'periodo'" view="month" dateFormat="mm/yy" showClear
      :manualInput="false" />

    <Label>Forma Pagamento:</Label>
    <Select v-model="selectedFormaPgto" :options="formas" optionLabel="descricao"
      placeholder="selecione uma forma de pgto" class="w-full" showClear scrollHeight="230" />

    <div class="flex flex-row gap-2 content-center items-center">
      <Button @click="filtrar()" class="w-full">
        <Search />
        Filtrar
      </Button>
      <Button severity="info" @click="limparFiltros()" class="w-full">
        <Eraser />
        Limpar
      </Button>
    </div>
  </div>

</template>

<style scoped></style>