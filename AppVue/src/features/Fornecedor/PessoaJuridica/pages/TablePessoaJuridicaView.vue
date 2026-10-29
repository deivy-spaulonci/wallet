<script setup lang="ts">
import type { Response } from "@/core/type/Response.ts";
import { tableUtil } from "@/core/util/TableUtil";
import Search from '@primeicons/vue/search';
import { onMounted, ref } from "vue";
import type { PessoaJuridica } from "../../model/pessoaJuridica";
import { pessoaJuridicaService } from "../../services/pessoa.juridica.service";

const { totalElements, page, onPage, onSort, loading, selectedItem} = tableUtil();

const pessoas = ref<PessoaJuridica[]>([])
const busca = ref('')

async function carregarPessoas() {
    loading.value = true;
    try {
        busca ? page.value = { ...page.value, busca: busca.value } : delete page.value.busca;
        const response: Response<PessoaJuridica> = await pessoaJuridicaService.findAll(page.value)

        pessoas.value = response.content;
        totalElements.value = response.totalElements;
    } finally {
        loading.value = false;
    }
}
onMounted(carregarPessoas);
</script>

<template>
    <div>
        <div class="flex flex-col bg-surface-100 dark:bg-surface-800 p-3">
            <DataTable :value="pessoas" stripedRows removableSort lazy paginator :sortOrder="1" :rows="page?.size"
                :sortField="page?.sort" :totalRecords="totalElements" :loading="loading"
                @page="onPage($event, carregarPessoas)"
                @sort="onSort($event, carregarPessoas)"
                :rowsPerPageOptions="[20, 25, 30]" v-model:selection="selectedItem" paginator-position="top"
                selectionMode="single" class="flex-col w-200">
                <template #header>
                    <div class="flex flex-row">
                        <InputText class="basis-3/6" v-model="busca" @input="carregarPessoas"/>
                        <Button @click="carregarPessoas" class="basis-1/6"><Search/> Buscar </Button>
                        <span class="basis-2/6 text-center">registros : {{ totalElements }} </span>
                    </div>

                </template>
                <Column field="nome" header="Nome" sortable></Column>
                <Column field="razaoSocial" header="Razão Social" sortable></Column>
                <Column field="cnpj" header="CNPJ" sortable>
                    <template #body="{ data }">
                        {{ data.cnpj }}
                    </template>
                </Column>
            </DataTable>
        </div>
    </div>
</template>
