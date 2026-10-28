import { createRouter, createWebHistory } from 'vue-router'

import DashBoardView from "@/features/Dashboard/DashBoardView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: () => import('@/components/layout/AppLayout.vue'),
      meta: {
        requiresAuth: true
      },
      children: [
        {
          path: '/',
          name: 'dashboard',
          component: DashBoardView,
        },
        {
          path: '/despesa',
          name:'despesa',
          component: () => import('@/features/Despesa/pages/DespesaView.vue'),
        },
        {
          path: '/juridica',
          name:'juridica',
          component: () => import('@/features/Fornecedor/PessoaJuridica/pages/PessoaJuridicaView.vue'),
        },
        {
          path: '/fisica',
          name:'fisica',
          component: () => import('@/features/Fornecedor/PessoaFisica/pages/PessoaFisicaView.vue'),
        },
        {
          path: '/conta',
          name:'conta',
          component: () => import('@/features/Conta/pages/ContaView.vue'),
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue')
    },
  ],
})

router.beforeEach((to) => {

  const token = localStorage.getItem('access_token');

  if (to.meta.requiresAuth && !token) {
    return {
      name: 'login'
    };
  }

  if (to.name === 'login' && token) {
    return {
      name: 'home'
    };
  }
});

export default router
