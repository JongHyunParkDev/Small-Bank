<template>
   <div>
        Wait...
   </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';

const router = useRouter();

init();
async function init () {
    try {
        router.push('/');
    }
    catch (error) {
        router.push('/Login');
    }
}
</script>
