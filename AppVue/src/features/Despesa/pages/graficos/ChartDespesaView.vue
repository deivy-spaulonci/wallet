<script lang="ts" setup>
import Tag from '@primeicons/vue/tag';
import Calendar from '@primeicons/vue/calendar';
import CalendarPlus from '@primeicons/vue/calendar-plus';
import TotalPorAnoDespesaView from './TotalPorAnoDespesaView.vue';
import TotalPorMesDespesaView from './TotalPorMesDespesaView.vue';
import TotalPorTipoDespesaView from './TotalPorTipoDespesaView.vue';

</script>

<template>
    <Tabs value="tab1">
        <TabList>
            <Tab value="tab1" ><Tag />Totais por tipo de despesa</Tab>
            <Tab value="tab2"><CalendarPlus />Totais por ano</Tab>
            <Tab value="tab3"><Calendar />Totais por mês</Tab>
        </TabList>
        <TabPanels>
            <TabPanel value="tab1">
                <TotalPorTipoDespesaView/>
            </TabPanel>
            <TabPanel value="tab2">
                <TotalPorAnoDespesaView />
            </TabPanel>
            <TabPanel value="tab3">
                <TotalPorMesDespesaView />
            </TabPanel>
        </TabPanels>
    </Tabs>
</template>