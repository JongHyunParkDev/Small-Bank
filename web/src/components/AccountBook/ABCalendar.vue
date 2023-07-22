<template>
    <div class="abcalendar">
        <div class="heaeder">
            <QBtn
                class="btn-left"
                color="white"
                text-color="black"
                icon="chevron_left"
                padding="sm"
                @click="setMonth(-1)"
            />
            <QInput
                class="abc-outer"
                input-class="abc-input"
                :dense="true"
                borderless
                v-model.number="nowYear"
                type="number"
                @update:model-value="updateYear"
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
                borderless
                v-model.number="nowMonth"
                type="number"
                @update:model-value="updateMonth"
            >
                <template v-slot:append>
                    <span class="input-append">
                        월
                    </span>
                </template>
            </QInput>
            <QBtn
                class="btn-right"
                color="white"
                text-color="black"
                icon="chevron_right"
                padding="sm"
                @click="setMonth(1)"
            />
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
import { dateToApiDateStr } from '@/lib/DateUtil';
import { AccountBookDay } from './models';
import { emit } from 'process';

// Date 객체
const now = ref(new Date());
// ui
const nowYear = ref(now.value.getFullYear());
const nowMonth = ref(now.value.getMonth() + 1);

const colArray = [
    '일요일',
    '월요일',
    '화요일',
    '수요일',
    '목요일',
    '금요일',
    '토요일',
];

const selectedABDay: Ref<AccountBookDay | undefined> = ref(undefined);
const dayArray: Ref<Array<Array<AccountBookDay>>> = ref([]);

updateCalendar();

function updateCalendar() {
    dayArray.value = [];
    selectedABDay.value = undefined;

    const firstDate = new Date(now.value);
    const lastDate = new Date(now.value);
    firstDate.setDate(1);
    lastDate.setMonth(lastDate.getMonth() + 1);
    lastDate.setDate(0);

    const tempArray: Array<AccountBookDay> = [];
    for (let i = 0; i < firstDate.getDay(); i++)
        tempArray.push({
            id: 'empty'
        });

    for (let i = 1; i <= lastDate.getDate(); i++) {
        const ABDay = {
            id: 'day',
            num: i,
            //TODO 거래 내역은 추후에 api 로 변경해야한다.
            account: {
                income: Math.floor(Math.random() * 10),
                spend: Math.floor(Math.random() * 10)
            }
        };
        tempArray.push(ABDay);
    }

    for (let i = 0; i < tempArray.length; i += 7) {
        const chunk: Array<AccountBookDay> = tempArray.slice(i, i + 7);

        while (chunk.length < 7) {
            chunk.push({id: 'empty'}); // 강제로 채우기
        }

        dayArray.value.push(chunk);
    }
}

function setMonth(num: number) {
    now.value.setMonth(now.value.getMonth() + num);

    nowMonth.value = now.value.getMonth() + 1;
    nowYear.value = now.value.getFullYear();

    updateCalendar();
}

function updateYear(num: number) {
    if (num < 1910) num = 1910;
    if (num > 2100) num = 2100;
    nowYear.value = num;

    now.value.setFullYear(num);

    updateCalendar();
}

function updateMonth(num: number) {
    if (num < 1) num = 1;
    if (num > 12) num = 12;
    nowMonth.value = num;

    now.value.setFullYear(nowYear.value);
    now.value.setMonth(num - 1);

    updateCalendar();
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
            now,
            nowYear,
            nowMonth,
            updateYear,
            updateMonth,
            setMonth
        };
    },
    methods: {
        selectDay(day: AccountBookDay) {
            selectedABDay.value = day;
            this.$emit('select-day', dateToApiDateStr(nowYear.value, nowMonth.value, day.num));
        }
    }
});
</script>

<style lang="scss" scoped>
.abcalendar {
    display: flex;
    flex-direction: column;

    > .heaeder {
        display: flex;
        margin: 5px auto;
        > .abc-outer {
            max-width: 100px;
            margin: 0px 5px;
            border-radius: 5px;
            box-shadow: 0 1px 5px rgba(0, 0, 0, 0.2), 0 2px 2px rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.12);

            ::v-deep .abc-input {
                text-align: center;
                font-size: 18px;
            }

            .input-append {
                font-weight: bold;
                font-size: 14px;
                padding-right: 5px;
            }
        }

        > .btn-right {
            margin-left: 10px;
        }

        > .btn-left {
            margin-right: 10px;
        }
    }

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
            position: relative;
            flex: 1;
            text-align: center;
            margin: 10px 2%;
            height: 70%;
        }
    }
}
</style>
