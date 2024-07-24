<template>
    <div class="abcalendar">
        <div class="header">
            <QBtn
                class="btn-left"
                icon="chevron_left"
                flat
                dense
                padding="sm"
                @click="setMonth(-1)"
            />
            <QInput
                class="abc-outer"
                dense
                borderless
                input-class="text-center"
                v-model.number="nowYear"
                @update:model-value="updateYear"
            />
            <QInput
                class="abc-outer"
                dense
                borderless
                input-class="text-center"
                v-model.number="nowMonth"
                @update:model-value="updateMonth"
            />
            <QBtn
                class="btn-right"
                icon="chevron_right"
                flat
                dense
                padding="sm"
                @click="setMonth(1)"
            />
        </div>
        <div class="row">
            <div
                class="col-item"
                v-for="(col, idx) in colArray"
                :key="idx"
                :class="convertClass(idx)"
            >
                {{ col }}
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
                :account-map="dayAccountTypeMap[item.num ? item.num : -1]"
                :selected-abday="selectedABDay"
                @selectDay="selectDay($event)"
            />
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, Ref, PropType, computed, onMounted, defineProps } from 'vue';
import ABDay from '@/components/AccountBook/ABDay.vue';
import { yearMonthDayToApiDateStr } from '@/lib/DateUtil';
import { AccountBookDay, DayAccount } from '@/types/AccountTypes';

// Date 객체
const now = ref(new Date());
now.value.setDate(1);
// ui
const nowYear = ref(now.value.getFullYear());
const nowMonth = ref(now.value.getMonth() + 1);

const colArray = ['일', '월', '화', '수', '목', '금', '토'];

const selectedABDay: Ref<AccountBookDay | undefined> = ref(undefined);
const dayArray: Ref<Array<Array<AccountBookDay>>> = ref([]);

const emit = defineEmits(['select-day', 'update-calendar']);

const props = defineProps({
    dayAccountArr: {
        type: Array as PropType<Array<DayAccount>>,
        required: true,
    },
});

const dayAccountTypeMap = computed(() => {
    const map = {};
    if (props.dayAccountArr)
        props.dayAccountArr.forEach((dayAccount) => {
            if (map[+dayAccount.date.substring(6, 8)] === undefined)
                map[+dayAccount.date.substring(6, 8)] = {};
            map[+dayAccount.date.substring(6, 8)][dayAccount.type] = true;
        });
    return map;
});

function convertClass(idx: number): string {
    if (idx === 0) return 'red';
    if (idx === 6) return 'blue';
    return '';
}

function updateCalendar() {
    dayArray.value = [];
    selectedABDay.value = undefined;

    emit('update-calendar', {
        year: nowYear.value,
        month: nowMonth.value,
    });

    const firstDate = new Date(now.value);
    const lastDate = new Date(now.value);
    firstDate.setDate(1);
    lastDate.setDate(1);
    lastDate.setMonth(lastDate.getMonth() + 1);
    lastDate.setDate(0);

    const tempArray: Array<AccountBookDay> = [];
    for (let i = 0; i < firstDate.getDay(); i++)
        tempArray.push({
            id: 'empty',
        });

    for (let i = 1; i <= lastDate.getDate(); i++) {
        const ABDay = {
            id: 'day',
            num: i,
        };
        tempArray.push(ABDay);
    }

    for (let i = 0; i < tempArray.length; i += 7) {
        const chunk: Array<AccountBookDay> = tempArray.slice(i, i + 7);

        while (chunk.length < 7) {
            chunk.push({ id: 'empty' }); // 강제로 채우기
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

function updateYear(value: string | number | null) {
    if (value === null) return;
    else if (typeof value === 'string') value = +value;
    if (value < 1910) value = 1910;
    if (value > 2100) value = 2100;
    nowYear.value = value;

    now.value.setFullYear(value);

    updateCalendar();
}

function updateMonth(value: string | number | null) {
    if (value === null) return;
    else if (typeof value === 'string') value = +value;
    if (value < 1) value = 1;
    if (value > 12) value = 12;
    nowMonth.value = value;

    now.value.setFullYear(nowYear.value);
    now.value.setMonth(value - 1);

    updateCalendar();
}

function selectDay(day: AccountBookDay) {
    selectedABDay.value = day;
    if (day.num) emit('select-day', yearMonthDayToApiDateStr(nowYear.value, nowMonth.value, day.num));
}

onMounted(() => {
    updateCalendar();
});
</script>

<style lang="scss" scoped>
.abcalendar {
    display: flex;
    flex-direction: column;

    > .header {
        display: flex;
        > .abc-outer {
            max-width: 50px;
            margin: 0px $spacing-sm;
        }
        > .btn-left {
            margin-right: $spacing-md;
        }
        > .btn-right {
            margin-left: $spacing-md;
        }
    }

    > .row {
        display: flex;

        > .col-item {
            flex: 1;
            text-align: center;
            padding: $spacing-md $spacing-sm;
        }

        > .red {
            color: $red;
        }

        > .blue {
            color: $blue;
        }
        > .row-item {
            position: relative;
            flex: 1;
            text-align: center;
            margin: $spacing-md 2%;
            height: 70%;
        }
    }
}
</style>
