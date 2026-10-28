<script setup lang="ts">

const valor = defineModel<string>({ required: false, default: '' })

function onValorInput(event: Event): void {
  const input = event.target as HTMLInputElement;
  const somenteNumeros = input.value.replace(/\D/g, "");

  const formatado = somenteNumeros
    ? (Number(somenteNumeros) / 100).toLocaleString("pt-BR", {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2,
      })
    : "0,00";

  input.value = formatado;
  valor.value = formatado;
}
</script>

<template>
  <InputText
      v-model="valor"
      @input="onValorInput"
      @keyup="onValorInput"
      class="w-52 text-right"/>
</template>

<style scoped>

</style>
