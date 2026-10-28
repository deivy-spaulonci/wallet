<script lang="ts" setup>
import BarCode from '@primeicons/vue/barcode';
import AlignJustify from '@primeicons/vue/align-justify';
import PlusCircle from '@primeicons/vue/plus-circle';
import ChartBar from '@primeicons/vue/chart-bar';
import TableContasView from './TableContasView.vue';
import FormContaView from './FormContaView.vue';
import ChartContaView from './graficos/ChartContaView.vue';
</script>


<template>
  <div class="flex-1 p-2 flex flex-col gap-2">
    <Message severity="success" class="font-bold text-3x1">
      <template #icon><BarCode /></template>Contas
    </Message>
     <div class="bg-surface-100 dark:bg-surface-800 flex-1 p-2 flex gap-1">
      <Tabs value="tab1" class="w-full">
        <TabList>
          <Tab value="tab1" class="flex items-center gap-2!">
            <AlignJustify />Consulta
          </Tab>
          <Tab value="tab2" class="flex items-center gap-2!">
            <PlusCircle />Cadastro
          </Tab>
          <Tab value="tab3" class="flex items-center gap-2!">
            <ChartBar />Gráfico
          </Tab>
        </TabList>
        <TabPanels>
          <TabPanel value="tab1">
            <TableContasView/>
          </TabPanel>
          <TabPanel value="tab2" class="min-h-215">
            <FormContaView/>
          </TabPanel>
          <TabPanel value="tab3" class="min-h-215">
            <ChartContaView/>
          </TabPanel>
        </TabPanels>
      </Tabs>
    </div>
    </div>

</template>

<style scoped>
</style>
