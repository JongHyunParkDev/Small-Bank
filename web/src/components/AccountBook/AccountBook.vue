<template>
    <div class="accountBook">
        <ABCalendar
            @selectDay="selectDay($event)"
        />
        <div class="history">

        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import ABCalendar from 'components/AccountBook/ABCalendar.vue';

function selectDay(a) {
    console.log(a.id);
}

export default defineComponent({
    name: 'AccountBook',
    components: { ABCalendar },
    setup() {

        return {selectDay };
    },
});
</script>

<style lang="scss" scoped>
.accountBook {
    width: 100%;
    display: flex;
    flex-direction: column;

    > .history {
        flex: 1;
        overflow-y: auto;
    }
}
</style>
