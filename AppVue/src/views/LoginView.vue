<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth.store';

const authStore = useAuthStore();
const router = useRouter();

const email = ref('');
const password = ref('');
const loading = ref(false);

async function login() {
  loading.value = true;

  try {
    await authStore.login({
      email: email.value,
      password: password.value
    });

    await router.push('/');
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <form @submit.prevent="login">

    <InputText
        v-model="email"
        type="email"
        placeholder="E-mail"
    />

    <InputPassword
        v-model="password"
        placeholder="Senha"
        :feedback="false"
    />

    <Button
        type="submit"
        label="Entrar"
        :loading="loading"
    />

  </form>
</template>