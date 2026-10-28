<script setup lang="ts">
import { onMounted } from "vue";
import MoneyBill from "@primeicons/vue/money-bill";
import BarCode from "@primeicons/vue/barcode";
import Dollar from "@primeicons/vue/dollar";
import Bell from "@primeicons/vue/bell";
import { Pie, Bar } from "vue-chartjs";
import { FormatUtil } from "@/core/util/FormatUtil";
import { dashBoardView } from "./DashBoardView";
import { DateUtil } from "@/core/util/DateUtil";

const {
    totalDespesa,
    totalContas,
    totalContasEmAberto,
    totalGastos,
    pieChartData,
    pieChartOptions,
    barChartData,
    barChartOptions,
    carregaContas,
    graficoDespesaAno,
    graficoEstatus,
    carregarValores,
    contas,
} = dashBoardView();

onMounted(() => {
    carregarValores();
    graficoDespesaAno();
    graficoEstatus();
    carregaContas();
});
</script>

<template>
    <div class="flex-1 p-4 flex flex-col gap-4">
        <div class="rounded-lg bg-surface-100 dark:bg-surface-800 h-auto">
            <div class="flex flex-row gap-5 p-3">
                <Card class="max-w-sm w-full h-35">
                    <template #title>
                        <div class="items-center text-blue-200 italic">
                            <MoneyBill :size="30" /> Total de Despesa
                        </div>
                    </template>
                    <template #content>
                        <div class="m-0 font-extrabold">
                            {{
                                FormatUtil.floatToNumberBR(Number(totalDespesa))
                            }}
                        </div>
                    </template>
                </Card>

                <Card class="max-w-sm w-full h-35">
                    <template #title>
                        <div class="items-center text-green-200 italic">
                            <BarCode :size="30" /> Total de Contas
                        </div>
                    </template>
                    <template #content>
                        <div class="m-0 font-extrabold">
                            {{
                                FormatUtil.floatToNumberBR(Number(totalContas))
                            }}
                        </div>
                    </template>
                </Card>

                <Card class="max-w-sm w-full h-35">
                    <template #title>
                        <div class="items-center text-yellow-200 italic">
                            <Bell :size="30" /> Total de Contas em Aberto
                        </div>
                    </template>
                    <template #content>
                        <div class="m-0 font-extrabold">
                            {{
                                FormatUtil.floatToNumberBR(
                                    Number(totalContasEmAberto),
                                )
                            }}
                        </div>
                    </template>
                </Card>

                <Card class="max-w-sm w-full h-35">
                    <template #title>
                        <div class="items-center text-red-200 italic">
                            <Dollar :size="30" /> Total de Gastos
                        </div>
                    </template>
                    <template #content>
                        <div class="m-0 font-extrabold">
                            {{
                                FormatUtil.floatToNumberBR(Number(totalGastos))
                            }}
                        </div>
                    </template>
                </Card>
            </div>
        </div>
        <div class="rounded-lg bg-surface-100 dark:bg-surface-800 p-3">
            <div class="flex flex-row gap-3">
                <div
                    class="basis-1/6 rounded-lg bg-surface-800 dark:bg-surface-900 p-3"
                >
                    <Pie :data="pieChartData" :options="pieChartOptions" />
                </div>

                <div
                    class="basis-5/6 rounded-lg bg-surface-800 dark:bg-surface-900 p-3"
                >
                    <Bar :data="barChartData" :options="barChartOptions" />
                </div>
            </div>
        </div>
        <div class="rounded-lg bg-surface-100 dark:bg-surface-800 p-3 flex-1">
            <div class="flex flex-row gap-3" v-if="contas">
                <div class="rounded-lg bg-surface-800 dark:bg-surface-900 p-3">
                    <div>Contas em aberto</div>
                    <DataTable ref="tabelaContas" :value="contas">
                        <Column field="nome" header="Despesa"></Column>
                        <Column
                            field="vencimento"
                            header="Vencimento"
                            class="w-30"
                        >
                            <template #body="{ data }">
                                <div class="text-center">
                                    {{ DateUtil.toFormDate(data.vencimento) }}
                                </div>
                            </template>
                        </Column>
                        <Column
                            field="parcela"
                            header="Parcela"
                            class="w-30 text-center"
                        >
                            <template #body="{ data }">
                                <div class="text-center">
                                    {{ data.parcela }} / {{ data.totalParcela }}
                                </div>
                            </template>
                        </Column>
                        <Column
                            field="valor"
                            header="Valor"
                            class="w-30"
                        >
                            <template #body="{ data }">
                                <div class="text-right">
                                    {{ FormatUtil.floatToNumberBR(data.valor) }}
                                </div>
                            </template>
                        </Column>
                        <Column field="status" header="Status" class="w-35">
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
                    </DataTable>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
