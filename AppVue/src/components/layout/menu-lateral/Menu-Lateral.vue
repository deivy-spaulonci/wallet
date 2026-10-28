<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from 'vue';
import { useAuthStore } from '@/stores/auth.store';
import Cog from '@primeicons/vue/cog';
import MoneyBill from '@primeicons/vue/money-bill';
import BarCode from '@primeicons/vue/barcode';
import Box from '@primeicons/vue/box';
import Building from '@primeicons/vue/building';
import Users from '@primeicons/vue/users';
import Home from '@primeicons/vue/home';
import PowerOff from '@primeicons/vue/power-off'

import Sidebar from 'primevue/sidebar';
import SidebarBackdrop from 'primevue/sidebarbackdrop';
import SidebarAside from 'primevue/sidebaraside';
import SidebarContent from 'primevue/sidebarcontent';
import SidebarFooter from 'primevue/sidebarfooter';
import SidebarGroup from 'primevue/sidebargroup';
import SidebarGroupContent from 'primevue/sidebargroupcontent';
import SidebarHeader from 'primevue/sidebarheader';
import SidebarMenu from 'primevue/sidebarmenu';
import SidebarMenuButton from 'primevue/sidebarmenubutton';
import SidebarMenuItem from 'primevue/sidebarmenuitem';
import SidebarMenuSub from 'primevue/sidebarmenusub';
import SidebarMenuSubButton from 'primevue/sidebarmenusubbutton';
import SidebarMenuSubItem from 'primevue/sidebarmenusubitem';
import SidebarPanel from 'primevue/sidebarpanel';
import SidebarSpacer from 'primevue/sidebarspacer';
import SidebarGroupLabel from 'primevue/sidebargrouplabel';

const isMobile = ref(false);
const open = ref(false);
const secondaryOpen = ref(true);
let mql = null;
let onMqlChange = null;

const authStore = useAuthStore();

onMounted(() => {
  if (typeof window === 'undefined') return;

  mql = window.matchMedia('(max-width: 800px)');
  isMobile.value = mql.matches;
  secondaryOpen.value = !isMobile.value;
  onMqlChange = (event: { matches: boolean; }) => {
    isMobile.value = event.matches;
    secondaryOpen.value = !event.matches;
  };
  mql.addEventListener('change', onMqlChange);
});

onBeforeUnmount(() => {
  // if (mql && onMqlChange) {
  //   mql.removeEventListener('change', onMqlChange);
  // }
});

async function handleLogout() {
  await authStore.logout();
}
</script>
<template>

  <SidebarBackdrop v-if="isMobile && (open || secondaryOpen)" class="absolute!" />
  <!-- Icon bar - always collapsed, opens on hover as overlay -->
  <Sidebar id="iconbar" side="left" :collapsible="isMobile ? 'offcanvas' : 'icon'" :overlay="true"
    :openOnHover="!isMobile" width="12rem" v-model:open="open">
    <SidebarSpacer />
    <SidebarAside>
      <SidebarPanel>
        <SidebarHeader>
          <SidebarMenu>
            <SidebarMenuItem>
              <SidebarMenuButton class="p-1!">
                <div class="flex size-6 shrink-0 items-center justify-center rounded-md
                     bg-emerald-600 text-white text-xs font-bold leading-none">
                  A
                </div>
                <span class="font-semibold text-sm">Acme</span>
              </SidebarMenuButton>
            </SidebarMenuItem>
          </SidebarMenu>
        </SidebarHeader>

        <SidebarContent>
          <SidebarGroup>
            <SidebarGroupLabel>Financeiro</SidebarGroupLabel>
            <SidebarGroupContent>
              <SidebarMenu>

                <SidebarMenuItem>
                  <RouterLink to="/">
                    <SidebarMenuButton>
                      <Home />
                      <span>Home</span>
                    </SidebarMenuButton>
                  </RouterLink>
                </SidebarMenuItem>

                <SidebarMenuItem>
                  <RouterLink to="/conta">
                    <SidebarMenuButton>
                      <BarCode />
                      <span>Contas</span>
                    </SidebarMenuButton>
                  </RouterLink>
                </SidebarMenuItem>

                <SidebarMenuItem>
                  <RouterLink to="/despesa">
                    <SidebarMenuButton>
                      <MoneyBill />
                      <span>Despesas</span>
                    </SidebarMenuButton>
                  </RouterLink>
                </SidebarMenuItem>

                <SidebarMenuItem :collapsible="true" :defaultOpen="true">
                  <SidebarMenuButton>
                    <Box />
                    <span>Fornecedor</span>
                  </SidebarMenuButton>
                  <SidebarMenuSub>
                    <SidebarMenuSubItem>
                      <RouterLink to="/juridica">
                        <SidebarMenuSubButton>
                          <Building />
                          Pessoa Juridíca
                        </SidebarMenuSubButton>
                      </RouterLink>
                    </SidebarMenuSubItem>
                    <SidebarMenuSubItem>
                      <RouterLink to="/fisica">
                      <SidebarMenuSubButton>
                        <Users />
                        Pessoa Fisíca
                      </SidebarMenuSubButton>
                      </RouterLink>
                    </SidebarMenuSubItem>
                  </SidebarMenuSub>
                </SidebarMenuItem>

              </SidebarMenu>
            </SidebarGroupContent>
          </SidebarGroup>
          <SidebarGroup>
            <SidebarGroupLabel>Perfil</SidebarGroupLabel>
            <SidebarGroupContent>
              <SidebarMenu>

                <SidebarMenuItem>
                  <RouterLink to="/">
                    <SidebarMenuButton @click="handleLogout">
                      <PowerOff />
                      <span>Logout</span>
                    </SidebarMenuButton>
                  </RouterLink>
                </SidebarMenuItem>
              </SidebarMenu>
            </SidebarGroupContent>
          </SidebarGroup>
        </SidebarContent>

        <SidebarFooter>
          <SidebarMenu>
            <SidebarMenuItem>
              <SidebarMenuButton>
                <Cog />
                <span>Settings</span>
              </SidebarMenuButton>
            </SidebarMenuItem>
          </SidebarMenu>
        </SidebarFooter>
      </SidebarPanel>
    </SidebarAside>
  </Sidebar>
</template>

<style scoped></style>