import { defineStore } from 'pinia';
import { computed, ref } from 'vue';
import { authService, type LoginRequest } from '@/core/service/auth.service';
import {useRouter} from "vue-router";

export const useAuthStore = defineStore('auth', () => {

    const router = useRouter();

    let token = ref(
        localStorage.getItem('access_token')
    );

    const isAuthenticated = computed(
        () => !!token
    );

    async function login(data: LoginRequest) {
        const response = await authService.login(data);
        token = response;
        localStorage.setItem(
            'access_token',
            response
        );
    }

    async function logout() {
        token.value = null;

        localStorage.removeItem('access_token');

        await router.push({
            name: 'login'
        });
    }

    return {
        token,
        isAuthenticated,
        login,
        logout
    };
});