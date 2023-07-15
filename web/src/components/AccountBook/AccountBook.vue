<template>
    <div class="accountBook">
        <ABCalendar />
        <div>

        </div>
    </div>

</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import ABCalendar from 'components/AccountBook/ABCalendar.vue';

export default defineComponent({
    name: 'AccountBook',
    components: { ABCalendar },
    setup() {

        return { };
    },
});
</script>

<style lang="scss" scoped>
.accountBook {
    width: 100%;
    display: flex;
    flex-direction: column;
}
</style>
