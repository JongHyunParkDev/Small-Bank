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
            <div
                class="row-item"
                v-for="(item, idx2) in row"
                :key="idx2"
                :class="convertClass(idx2)"
            >
                {{item.num}}
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { dateToDateStr } from '@/lib/DateUtil';

type DayOfTheWeek = {
  name: string;
  class?: string;
};

type ABDay = {
  id: string;
  num?: number;
};


const now = new Date();

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

const tempArray: Array<ABDay> = [];
for (let i = 0; i < firstDate.getDay(); i++)
    tempArray.push({
        id: 'empty'
    });

for (let i = 1; i <= lastDate.getDate(); i++)
    tempArray.push({
        id: 'day',
        num: i
    });

const dayArray: Array<Array<ABDay>> = [];
for (let i = 0; i < tempArray.length; i += 7) {
    const chunk: Array<ABDay> = tempArray.slice(i, i + 7);

    while (chunk.length < 7) {
        chunk.push({id: 'empty'}); // 강제로 null 값으로 채우기
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
    setup() {


        return { colArray, dayArray, convertClass};
    },
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
            padding: 15px 10px;
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
            padding: 20px 10px;
        }
    }
}
</style>
