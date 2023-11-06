<template>
    <RouterView/>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/AuthStore';

const authStore = useAuthStore();

init();
async function init () {
    await authStore.login();
}
</script>
