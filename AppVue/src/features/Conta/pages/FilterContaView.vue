<script lang="ts" setup>
import { onMounted } from "vue";
import { filterContaView } from "./FilterContaView";
import Search from "@primeicons/vue/search";
import Eraser from "@primeicons/vue/eraser";
import Times from "@primeicons/vue/times";

const emit = defineEmits(["filtrar"]);

const {
    tipos,
    selectedTipoConta,
    vencimentoFinal,
    vencimentoInicial,
    mesDate,
    opcPeriodo,
    statusSelected,
    statusArray,
    carregarTipos,
    filtrar,
    limparFiltros,
} = filterContaView(emit);

onMounted(() => {
    carregarTipos();
});
</script>

<template>
    <div class="flex flex-col gap-2 p-4 rounded-lg bg-surface-100 dark:bg-surface-800">
        <Divider type="dashed">
            <code class="uppercase text-xs">Filtros</code>
        </Divider>

        <Label>Tipo Conta:</Label>
        <Select
            v-model="selectedTipoConta"
            :options="tipos"
            optionLabel="nome"
            placeholder="selecione uma conta"
            class="w-full"
            scrollHeight="30rem"
            showClear
        />

        <div class="flex flex-row gap-2 content-center items-center">
            <RadioButton
                v-model="opcPeriodo"
                inputId="opc-periodo"
                value="periodo"
            />
            <Label for="opc-periodo">Periodo Vencimento:</Label>
        </div>
        <div class="flex flex-row w-full gap-2">
            <IconField>
                <InputText
                    :disabled="opcPeriodo == 'mes'"
                    v-model="vencimentoInicial"
                    v-mask="{ mask: '99/99/9999', slotChar: 'dd/mm/yyyy' }"
                    placeholder="dd/mm/yyyy"
                    class="w-35"
                />
                <InputIcon
                    v-if="vencimentoInicial"
                    class="cursor-pointer"
                    @click="vencimentoInicial = ''"
                >
                    <Times />
                </InputIcon>
            </IconField>
            <span class="w-full text-center">até</span>
            <IconField>
                <InputText
                    :disabled="opcPeriodo == 'mes'"
                    v-model="vencimentoFinal"
                    v-mask="{ mask: '99/99/9999', slotChar: 'dd/mm/yyyy' }"
                    placeholder="dd/mm/yyyy"
                    class="w-35"
                />
                <InputIcon
                    v-if="vencimentoFinal"
                    class="cursor-pointer"
                    @click="vencimentoFinal = ''"
                >
                    <Times />
                </InputIcon>
            </IconField>
        </div>
        <div class="flex flex-row gap-2 content-center items-center">
            <RadioButton v-model="opcPeriodo" inputId="opc-mes" value="mes" />
            <Label for="opc-mes">Vencimento por mês:</Label>
        </div>
        <DatePicker
            v-model="mesDate"
            :disabled="opcPeriodo == 'periodo'"
            view="month"
            dateFormat="mm/yy"
            showClear
            :manualInput="false"
        />

        <Label>Status:</Label>
        <Select
            v-model="statusSelected"
            :options="statusArray"
            optionLabel="label"
            optionValue="value"
            scrollHeight="30rem"
            placeholder="Selecione um status"
            class="w-full"
        />

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
