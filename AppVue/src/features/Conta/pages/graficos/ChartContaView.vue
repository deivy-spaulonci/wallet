<script lang="ts" setup>
import Tag from '@primeicons/vue/tag';
import Calendar from '@primeicons/vue/calendar';
import CalendarPlus from '@primeicons/vue/calendar-plus';
import TotalPorTipoContaView from './TotalPorTipoContaView.vue';
import TotalPorStatusView from './TotalPorStatusView.vue';

</script>

<template>
    <Tabs value="tab1">
        <TabList>
            <Tab value="tab1" ><Tag />Totais por tipo de conta</Tab>
            <Tab value="tab2"><CalendarPlus />Totais por status</Tab>
        </TabList>
        <TabPanels>
            <TabPanel value="tab1">
                <TotalPorTipoContaView/>
            </TabPanel>
            <TabPanel value="tab2">
                <TotalPorStatusView />
            </TabPanel>
        </TabPanels>
    </Tabs>
</template>
