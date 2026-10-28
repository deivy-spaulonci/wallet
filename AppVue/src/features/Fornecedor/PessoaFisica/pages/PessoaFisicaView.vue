<script setup lang="ts">
import Users from '@primeicons/vue/users';
import FormPessoaFisicaView from './FormPessoaFisicaView.vue';
import TableDespesaView from '@/features/Despesa/pages/TableDespesaView.vue';
import TablePessoaFisicaView from './TablePessoaFisicaView.vue';
</script>


<template>
  <div class="flex-1 p-2 flex flex-col gap-2">
    <Message severity="success" class="font-bold text-3x1">
      <template #icon>
        <Users />
      </template>
      Pessoa Fisica
    </Message>
    <div class="flex flex-row gap-6 p-6">
        <FormPessoaFisicaView/>
        <TablePessoaFisicaView/>
    </div>
    
  </div>
  
</template>