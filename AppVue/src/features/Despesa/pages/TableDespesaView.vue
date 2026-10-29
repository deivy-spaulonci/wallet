<script setup lang="ts" >
import { onMounted } from 'vue';
import { tableDespesaView  } from './TableDespesaView'
import { FormatUtil } from "@/core/util/FormatUtil";
import { DateUtil } from "@/core/util/DateUtil";
import FilterDespesaView from './FilterDespesaView.vue';
import FormDespesaView from './FormDespesaView.vue';
import InfoCircle from "@primeicons/vue/info-circle";
import DetailDespesaView from './DetailDespesaView.vue';

const { despesas, menuModel, loading, selectedItem, totalElements, page, valorTotal, cm, editVisible,
        onRowContextMenu, carregarDespesas, onPage, onSort, filtrar } = tableDespesaView();

 defineExpose({ filtrar })

 onMounted(carregarDespesas);
</script>

<template>
  <div class="flex gap-1">
    <div class="flex flex-col rounded-lg bg-surface-100 dark:bg-surface-800 gap-3 p-3">
      <ContextMenu ref="cm" :model="menuModel" />
      <DataTable :value="despesas" stripedRows removableSort lazy paginator :sortOrder="-1" :rows="page?.size"
        :sortField="page?.sort" :totalRecords="totalElements" :loading="loading"
        @page="onPage($event, carregarDespesas)"
        @sort="onSort($event, carregarDespesas)"
        :rowsPerPageOptions="[15, 25, 30]" v-model:selection="selectedItem" paginator-position="top"
        selectionMode="single" dataKey="uuid" class="flex-col" @row-contextmenu="onRowContextMenu" contextMenu>
        <template #header>
          <div class="flex gap-1">
            <Message severity="success" class="flex-row w-50">
              Total : {{ FormatUtil.floatToNumberBR(valorTotal) }}
            </Message>
            <divider layout="vertical" class="flex-row  flex-auto" />
            <Message severity="info" class="flex-row w-50">
              Registos: {{ totalElements }}
            </Message>
          </div>
        </template>
        <Column field="tipoDespesa" header="Despesa" sortable></Column>
        <Column field="fornecedor" header="Fornecedor" sortable></Column>
        <Column field="dataPagamento" header="Data" sortable class="w-30">
          <template #body="{ data }">{{ DateUtil.toFormDate(data.dataPagamento) }}</template>
        </Column>
        <Column field="formaPagamento" header="Forma Pagamento" sortable></Column>
        <Column field="valor" header="Valor" sortable class="w-30">
          <template #body="{ data }">
            <div class="text-right">{{ FormatUtil.floatToNumberBR(data.valor) }}</div>
          </template>
        </Column>
        <template #empty>
          <div class="flex flex-row gap-4 items-center text-yellow-400 italic">
            <InfoCircle /> sem despesas encontradas.
          </div>
        </template>
      </DataTable>
      <Toast />
      <ConfirmDialog></ConfirmDialog>
    </div>
    <div class="flex flex-col max-w-100 gap-3">
      <FilterDespesaView @filtrar="filtrar" ref="filterDespesa" />
      <DetailDespesaView :despesaDetail="selectedItem" v-if="selectedItem"/>
    </div>
  </div>

<Dialog v-model:visible="editVisible" modal header="Editar Despesa" :style="{width: '510px'}" @after-hide="carregarDespesas">
    <FormDespesaView :idEdicao="selectedItem.uuid" @close-dialog="editVisible = false" />
    <template #footer>
      <Button severity="secondary" @click="editVisible = false">Fechar</Button>
    </template>
</Dialog>
</template>

<style scoped></style>
