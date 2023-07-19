<template>
    <div class="abcalendar">
        <div class="heaeder">
            <QInput
                class="abc-outer"
                input-class="abc-input"
                :dense="true"
                outlined
                v-model.number="nowYear"
                type="number"
                @update="updateYear"
            >
                <template v-slot:append>
                    <span class="input-append">
                        년
                    </span>
                </template>
            </QInput>
            <QInput 
            class="abc-outer"
                input-class="abc-input"
                :dense="true"
                outlined
                v-model.number="nowMonth" 
                type="number"
                @update="updateMonth"
            >
                <template v-slot:append>
                    <span class="input-append">
                        월
                    </span>
                </template>
            </QInput>
        </div>
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

const now = new Date();

const nowYear = ref(now.getFullYear());
const nowMonth = ref(now.getMonth());
const nowDate = now.getDate();

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
    if (i === nowDate) selectedABDay.value = ABDay;
}

const dayArray: Array<Array<AccountBookDay>> = [];
for (let i = 0; i < tempArray.length; i += 7) {
    const chunk: Array<AccountBookDay> = tempArray.slice(i, i + 7);

    while (chunk.length < 7) {
        chunk.push({id: 'empty'}); // 강제로 채우기
    }

    dayArray.push(chunk);
}

function updateYear(num1: number, num2: number) {
    console.log(num1, num2);
    if (num1 < 1910) num1 = 1910; 
    if (num1 > 2100) num1 = 2100;
    nowMonth.value = num1;
}

function updateMonth(num1: number, num2: number) {
    console.log(num1, num2);
    if (num1 < 1) num1 = 1; 
    if (num1 > 12) num1 = 12;
    nowMonth.value = num1;
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
            nowYear,
            nowMonth,
            updateYear,
            updateMonth,
        
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

    > .heaeder {
        display: flex;
        margin: 5px auto;
        > .abc-outer {
            max-width: 100px;
            margin: 0px 5px;

            ::v-deep .abc-input {
                text-align: center;
                font-size: 18px;
            }
            
            .input-append {
                font-weight: bold;
                font-size: 18px;
            }
        }
    }

    > .row {
        display: flex;
        height: 6%;

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
