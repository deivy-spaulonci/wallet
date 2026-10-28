<script setup lang="ts">
import Building from '@primeicons/vue/building';
import FormPessoaJuridicaView from './FormPessoaJuridicaView.vue';
import TablePessoaJuridicaView from './TablePessoaJuridicaView.vue';
</script>

<template>
  <div class="flex-1 p-2 flex flex-col gap-2">
    <Message severity="success" class="font-bold text-3x1">
      <template #icon>
        <Building />
      </template>
      Pessoa Juridica
    </Message>
    <div class="flex flex-row gap-6 p-6">
      <FormPessoaJuridicaView />
      <TablePessoaJuridicaView />
    </div>
    
  </div>
  
</template>
