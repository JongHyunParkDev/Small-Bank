<template>
    <RouterView/>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/AuthStore';
import { useQuasar } from 'quasar'

const $q = useQuasar();
const router = useRouter();
const authStore = useAuthStore();

init();
async function init () {
    try {
        await authStore.login();
    }
    catch (err) {
        router.push('/login');
    }
}
</script>
