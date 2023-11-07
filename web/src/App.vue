<template>
    <RouterView/>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/AuthStore';
import { useRouter } from 'vue-router';
const authStore = useAuthStore();
const router = useRouter();

init();
async function init () {
    try {
        await authStore.login();
        router.push('/');
    }
    catch (error) {
        router.push('/login');
    }
}
</script>
