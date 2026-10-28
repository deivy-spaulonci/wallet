<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { FormatUtil } from "@/core/util/FormatUtil";
import { DateUtil } from "@/core/util/DateUtil";
import Bell from "@primeicons/vue/bell";
import ExclamationTriangle from "@primeicons/vue/exclamation-triangle";
import Verified from "@primeicons/vue/verified";
import Check from "@primeicons/vue/check";
import FilterContaView from "./FilterContaView.vue";
import InfoCircle from "@primeicons/vue/info-circle";
import CheckSquare from "@primeicons/vue/check-square";
import { tableContasView } from "./TableContasView.ts";
import SetContaView from "./SetContaView.vue";
import ContaDetailView from "./ContaDetailView.vue";

const {
    totalElements,
    page,
    onPage,
    onSort,
    loading,
    selectedItem,
    cm,
    menuModel,
    onRowContextMenu,
    contas,
    contaDetail,
    valorTotal,
    carregarContas,
    findConta,
    rowStyle,
    filtrar,
    setPayVisible
} = tableContasView();

defineExpose({ filtrar });

onMounted(carregarContas);
</script>

<template>
    <div class="flex gap-1">
        <div class="flex flex-col">
            <ContextMenu ref="cm" :model="menuModel" />
            <DataTable
            ref="tabelaContas"
                :value="contas"
                stripedRows
                removableSort
                lazy
                paginator
                :sortOrder="-1"
                :rows="page?.size"
                :sortField="page?.sort"
                :totalRecords="totalElements"
                :loading="loading"
                @page="onPage($event, carregarContas)"
                @sort="onSort($event, carregarContas)"
                :rowsPerPageOptions="[15, 20, 30]"
                v-model:selection="selectedItem"
                paginator-position="top"
                contextMenu
                selectionMode="single"
                dataKey="uuid"
                class="flex-col w-220 tabelaconta"
                :rowStyle="rowStyle"
                @row-select="findConta"
                @row-contextmenu="onRowContextMenu"
            >
                <template #header>
                    <div class="flex gap-1">
                        <Message severity="success" class="flex-row w-50">
                            Total : {{ FormatUtil.floatToNumberBR(valorTotal) }}
                        </Message>
                        <divider layout="vertical" class="flex-row flex-auto" />
                        <Message severity="info" class="flex-row w-50">
                            Registos: {{ totalElements }}
                        </Message>
                    </div>
                </template>
                <Column field="nome" header="Despesa" sortable></Column>
                <Column field="vencimento" header="Vencimento" sortable class="w-30">
                    <template #body="{ data }">
                        <div class="text-center">
                            {{ DateUtil.toFormDate(data.vencimento) }}
                        </div>
                    </template>
                </Column>
                <Column field="parcela" header="Parcela" class="w-30 text-center">
                    <template #body="{ data }">
                        <div class="text-center">
                            {{ data.parcela }} / {{ data.totalParcela }}
                        </div>
                    </template>
                </Column>
                <Column field="valor" header="Valor" sortable class="w-30">
                    <template #body="{ data }">
                        <div class="text-right">
                            {{ FormatUtil.floatToNumberBR(data.valor) }}
                        </div>
                    </template>
                </Column>
                <Column field="status" header="Status" sortable class="w-35">
                    <template #body="{ data }">
                        <span v-if="data.intStatus == 0" class="flex flex-row align-center items-center gap-2 text-yellow-500">
                            <Bell size="20"/> {{ data.status }}
                        </span>
                        <span v-if="data.intStatus == -1" class="flex flex-row align-center items-center gap-2 text-red-500">
                            <ExclamationTriangle size="16"/>{{ data.status }}
                        </span>
                        <span v-if="data.intStatus == 1" class="flex flex-row align-center items-center gap-2 text-blue-500">
                            <Check size="16"/> {{ data.status }}
                        </span>
                        <span v-if="data.intStatus == 2" class="flex flex-row align-center items-center gap-2 text-green-500">
                            <Verified size="16"/> {{ data.status }}
                        </span>
                    </template>
                </Column>
                <Column header="Pagar" class="w-10">
                    <template #body="{ data }">
                        <Button v-if="data.intStatus !== 2"  iconOnly variant="text" class="p-0 m-0"
                        @click="setPayVisible = true, selectedItem = data;">
                            <CheckSquare size="20" class="p-0 m-0"/>
                        </Button>
                    </template>
                </Column>
                <template #empty>
                    <div class="flex flex-row gap-4 items-center text-yellow-400 italic">
                        <InfoCircle /> No products found.
                    </div>
                </template>
            </DataTable>
            <ConfirmDialog></ConfirmDialog>
        </div>
        <div class="flex flex-col w-100">
            <FilterContaView @filtrar="filtrar" />

            <ContaDetailView :contaDetail="contaDetail"/>
        </div>
    </div>
    <SetContaView v-model:visible="setPayVisible" :conta="selectedItem" @atualizar="carregarContas"></SetContaView>
    <Toast />
</template>

<style scoped>
:deep(.tabelaconta .p-datatable-tbody > tr.p-datatable-row-selected),
:deep(.tabelaconta .p-datatable-striped .p-datatable-tbody > tr.p-datatable-row-selected),
:deep(.tabelaconta .p-datatable-striped .p-datatable-tbody > tr.p-datatable-row-selected > td) {
  background-color: var(--p-green-800) !important;
  color: #fff !important;
}
</style>
