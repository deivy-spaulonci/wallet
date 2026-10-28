<script setup lang="ts">
import FormDespesaView from "@/features/Despesa/pages/FormDespesaView.vue";
import TableDespesaView from "@/features/Despesa/pages/TableDespesaView.vue";
import AlignJustify from '@primeicons/vue/align-justify';
import PlusCircle from '@primeicons/vue/plus-circle';
import MoneyBill from '@primeicons/vue/money-bill';
import ChartBar from '@primeicons/vue/chart-bar';
import ChartDespesaView from "@/features/Despesa/pages/graficos/ChartDespesaView.vue";

</script>

<template>
  <div class="flex-1 p-2 flex flex-col gap-2">
    <Message severity="success" class="font-bold text-3x1">
      <template #icon>
        <MoneyBill />
      </template>
      Despesas
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
            <TableDespesaView ref="tableDespesaViewRef" />          
          </TabPanel>
          <TabPanel value="tab2" class="min-h-215">
            <FormDespesaView />
          </TabPanel>
          <TabPanel value="tab3" class="min-h-215">
            <ChartDespesaView/>
          </TabPanel>
        </TabPanels>
      </Tabs>
    </div>
  </div>
</template>

<style scoped></style>