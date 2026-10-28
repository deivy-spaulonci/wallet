<script setup lang="ts">
import type { Response } from "@/core/type/Response.ts";
import { tableUtil } from "@/core/util/TableUtil";
import Search from '@primeicons/vue/search';
import { onMounted, ref } from "vue";
import type { PessoaFisica } from "../../model/pessoaFisica";
import { pessoaFisicaService } from "../../services/pessoa.fisica.service";

const { totalElements, page, onPage, onSort, loading, selectedItem} = tableUtil();

const pessoas = ref<PessoaFisica[]>([])
const busca = ref('')

async function carregarPessoas() {
   loading.value = true;
   try {
      busca ? page.value = { ...page.value, busca: busca.value } : delete page.value.busca;
      const response: Response<PessoaFisica> = await pessoaFisicaService.findAll(page.value)

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
      <div class="flex flex-col">
         <DataTable :value="pessoas" stripedRows removableSort lazy paginator :sortOrder="1" :rows="page?.size"
            :sortField="page?.sort" :totalRecords="totalElements" :loading="loading" 
            @page="onPage($event, carregarPessoas)" 
            @sort="onSort($event, carregarPessoas)"
            :rowsPerPageOptions="[20, 25, 30]" v-model:selection="selectedItem" paginator-position="top"
            selectionMode="single" class="flex-col w-150">
            <template #header>
               <div class="flex flex-row">
                  <InputText class="basis-3/6" v-model="busca" @input="carregarPessoas" />
                  <Button class="basis-1/6" @click="carregarPessoas">
                     <Search /> Buscar
                  </Button>
                  <span class="basis-2/6 text-center">registros : {{ totalElements }} </span>
               </div>

            </template>
            <Column field="nome" header="Nome" sortable></Column>
            <Column field="cpf" header="CPF" sortable></Column>
         </DataTable>
      </div>
   </div>
</template>