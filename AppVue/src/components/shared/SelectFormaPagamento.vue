<script setup lang="ts">
import {onMounted, ref} from "vue";
import {formatPagamentoService} from "@/features/FormaPagamento/service/forma.pagamento.service.ts";
import type {FormaPagamento} from "@/features/FormaPagamento/model/FormaPagamento.ts";

const selectedFormaPgto = defineModel("selectedFormaPgto",{ required: false })
const selectedOne = defineModel("selectedOne",{ required: false })

const formas = ref<FormaPagamento[]>([])

async function carregarFormas() {
  try {
    formas.value = await formatPagamentoService.findAll();
    if (selectedOne.value)
      selectedFormaPgto.value = formas.value[0];
  } catch (e) {
    console.log('Erro ao consultar formas de pagamento.');
  }
}

onMounted(() => {
  carregarFormas()
});
</script>

<template>
  <Select v-model="selectedFormaPgto"
          :options="formas"
          optionLabel="descricao"
          placeholder="selecione uma forma de pgto"
          inputClass="w-full"
          class="w-100"
          scrollHeight="40rem"/>
</template>

<style scoped>

</style>
