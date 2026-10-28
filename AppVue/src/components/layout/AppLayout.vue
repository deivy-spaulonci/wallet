<script setup lang="ts">
import MenuLateral from "@/components/layout/menu-lateral/Menu-Lateral.vue";
import SidebarMain from 'primevue/sidebarmain';
import SidebarLayout from 'primevue/sidebarlayout';
</script>

<template>
  <div class="overflow-hidden">
    <SidebarLayout class="min-h-full relative!">
      <MenuLateral/>
      <SidebarMain>
        <RouterView />
      </SidebarMain>
    </SidebarLayout>
  </div>
</template>

<style scoped>
</style>