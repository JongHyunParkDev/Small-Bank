<template>
    <div class="abcalendar">
        <div class="row">
            <div
                class="col-item strong"
                v-for="(col, idx) in colArray"
                :key="idx"
                :class="convertClass(idx)"
            >
                {{col}}
            </div>
        </div>
        <div
            class="row"
            v-for="(row, idx) in dayArray"
            :key="idx"
        >
            <ABDay
                class="row-item"
                v-for="(item, idx2) in row"
                :key="idx2"
                :class="convertClass(idx2)"
                :item="item"
                :selected-abday="selectedABDay"
                @selectDay="selectDay($event)"
            />
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref, Ref } from 'vue';
import ABDay from '@/components/AccountBook/ABDay.vue';
import { dateToDateStr } from '@/lib/DateUtil';
import { AccountBookDay } from './models';
import { emit } from 'process';

const now = new Date().getDate();
const firstDate = new Date();
const lastDate = new Date();
firstDate.setDate(1);
lastDate.setMonth(lastDate.getMonth() + 1);
lastDate.setDate(0);

const colArray = [
    '일요일',
    '월요일',
    '화요일',
    '수요일',
    '목요일',
    '금요일',
    '토요일',
];

const tempArray: Array<AccountBookDay> = [];
for (let i = 0; i < firstDate.getDay(); i++)
    tempArray.push({
        id: 'empty'
    });

    
const selectedABDay: Ref<AccountBookDay | undefined> = ref(undefined);

for (let i = 1; i <= lastDate.getDate(); i++) {
    const ABDay = {
        id: 'day',
        num: i,
        account: {
            income: Math.floor(Math.random() * 10),
            spend: Math.floor(Math.random() * 10)
        }
    };
    tempArray.push(ABDay);
    if (i === now) selectedABDay.value = ABDay;
}

const dayArray: Array<Array<AccountBookDay>> = [];
for (let i = 0; i < tempArray.length; i += 7) {
    const chunk: Array<AccountBookDay> = tempArray.slice(i, i + 7);

    while (chunk.length < 7) {
        chunk.push({id: 'empty'}); // 강제로 채우기
    }

    dayArray.push(chunk);
}

function convertClass (idx: number): string {
    if (idx === 0) return 'red';
    if (idx === 6) return 'blue';
    return '';
}

export default defineComponent({
    name: 'ABCalendar',
    emits: ['select-day'],
    components: {
        ABDay,
    },
    setup() {
        return { 
            selectedABDay, 
            colArray, 
            dayArray, 
            convertClass, 
        };
    },
    methods: {
        selectDay(day: AccountBookDay) {
            selectedABDay.value = day;
            this.$emit('select-day', selectedABDay.value);
        }
    }
});
</script>

<style lang="scss" scoped>
.abcalendar {
    flex: 1;
    display: flex;
    flex-direction: column;

    > .row {
        display: flex;

        > .col-item {
            flex: 1;
            text-align: center;
            padding: 10px 5px;
        }

        > .red {
            color: red;
        }

        > .blue {
            color: blue;
        }

        > .strong {
            font-weight: bold;
        }

        > .row-item {
            flex: 1;
            text-align: center;
            margin: 10px 2%;
        }
    }
}
</style>
