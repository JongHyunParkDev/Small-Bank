<template>
    <QPage class="row">
        <AccountBook active />
    </QPage>
</template>

<script lang="ts">
import AccountBook from 'components/AccountBook/AccountBook.vue';
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'AccountBookPage',
    components: { AccountBook },
});
</script>
