<template>
    <div />
</template>

<script setup lang="ts">
import { defineProps } from 'vue';

const props = defineProps({
    selectedSurveyIdx: {
        type: Number,
        required: true,
    },
});
</script>

<style lang="scss" scoped></style>
