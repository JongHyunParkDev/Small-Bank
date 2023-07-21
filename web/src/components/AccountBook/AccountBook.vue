<template>
    <div class="accountBook">
        <ABCalendar
            class="calendar"
            @selectDay="selectDay($event)"
        />
        <div class="divider" />
        <ABHistory
            class="history"
            :day-account-arr="dayAccountArr"
        />
        <QFab
            class="fab"
            color="primary"
            icon="keyboard_arrow_up"
            direction="up">
            <QFabAction
                color="primary"
                icon="list"
            />
            <QFabAction
                color="primary"
                icon="add"
            />
        </QFab>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref, Ref } from 'vue';
import ABCalendar from 'components/AccountBook/ABCalendar.vue';
import ABHistory from 'components/AccountBook/ABHistory.vue';

//TODO 거래 내역은 추후에 api 로 변경해야한다.
// id, timestamp, text, money, category, type
const dayAccountArr: Ref<Array<any>> = ref([]);

function selectDay(a) {
    console.log(a);
    const spendSize = Math.floor(Math.random() * 7) + 20;
    const incomeSize = Math.floor(Math.random() * 30) < 1 ? 0 : 1;

    dayAccountArr.value = [];

    for (let i = 0; i < spendSize; i++) {
        dayAccountArr.value.push({
            time: '00:00', // DateUtil.getTime
            text: '지출 ' + i,
            money: (Math.floor(Math.random() * 10) + 1) * 1000,
            type: 'spend'
        })
    }

    for (let i = 0; i < incomeSize; i++) {
        dayAccountArr.value.push({
            time: '00:00', // DateUtil.getTime
            text: '소득 ' + i,
            money: Math.floor(Math.random() * 5) * 1000,
            type: 'income'
        })
    }

}

export default defineComponent({
    name: 'AccountBook',
    components: {
        ABCalendar,
        ABHistory
    },
    setup() {

        return {selectDay, dayAccountArr};
    },
});
</script>

<style lang="scss" scoped>
.accountBook {
    position: absolute;
    top: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;

    > .calendar {
        position: relative;
    }

    > .divider {
        border-top: 0.5px $grey-5 solid;
    }

    > .history {
        flex: 1;
        position: relative;
    }

    > .fab {
        position: fixed;
        bottom: 20px;
        right: 20px;
    }
}
</style>
