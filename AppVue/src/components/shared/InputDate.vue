<script setup lang="ts">
const data = defineModel<string>({ required: false, default: '' })
</script>

<template>
  <InputText id="prev-date"
             v-model="data"
             v-mask="{ mask: '99/99/9999', slotChar: 'dd/mm/yyyy' }"
             placeholder="dd/mm/yyyy"
             class="w-35 text-center"/>
</template>

<style scoped>

</style>