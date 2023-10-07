<template>
    <div class="account-book-detail">
        <div class="header">
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
        <div class="chart-container">
            <div ref="chart">

            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, Ref, inject, onMounted } from 'vue';
import { process } from '@/lib/Async';
import { Api } from '@/lib/Api';
import { dateToApiDateStr } from '@/lib/DateUtil';
import { DayAccount } from '@/types/AccountTypes';
import HighCharts from 'highcharts';
import VueHighchart from 'VueHighchart';

const chart = ref<HTMLInputElement | null>(null);

const upProcessSpinner = inject<() => void>('upProcessSpinner');
const downProcessSpinner = inject<() => void>('downProcessSpinner');

const now = ref(new Date());

const nowYear = ref(now.value.getFullYear());
const nowMonth = ref(now.value.getMonth() + 1);

function setMonth(num: number) {
    now.value.setMonth(now.value.getMonth() + num);

    nowMonth.value = now.value.getMonth() + 1;
    nowYear.value = now.value.getFullYear();

    getAccounts();
}

function updateYear(num: number) {
    if (num < 1910) num = 1910;
    if (num > 2100) num = 2100;
    nowYear.value = num;

    now.value.setFullYear(num);

    getAccounts();
}

function updateMonth(num: number) {
    if (num < 1) num = 1;
    if (num > 12) num = 12;
    nowMonth.value = num;

    now.value.setFullYear(nowYear.value);
    now.value.setMonth(num - 1);

    getAccounts();
}

function getAccounts() {
    const year = nowYear.value;
    const month = nowMonth.value;

    const lastDay = new Date(year, month, 0).getDate();

    const startDate = dateToApiDateStr(year, month, 1);
    const endDate = dateToApiDateStr(year, month, lastDay);

    // 사실 ProcesSpinner 가 없는 경우는 없다. typescript 을 위해서...
    if (upProcessSpinner && downProcessSpinner) {
        process(upProcessSpinner, downProcessSpinner, async () => {
            const dayAccountList: Array<DayAccount> = await Api.get('user/accounts', {
                startDate: startDate,
                endDate: endDate,
            });

            console.log(dayAccountList);
        });
    }
}

onMounted(() => {
    HighCharts.chart(chart, {
        chart: {
            type: 'pie'
        },
        title: {
            text: 'Browser market shares. January, 2022',
            align: 'left'
        },
    })
})
</script>

<style lang="scss" scoped>
.account-book-detail {
    position: absolute;
    top: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    padding: $spacing-sm;

    > .header {
        display: flex;
        margin: $spacing-sm auto;

        > .abc-outer {
            max-width: 100px;
            margin: 0px $spacing-sm;
            border-radius: $spacing-sm;
            box-shadow: 0 1px $spacing-sm rgba(0, 0, 0, 0.2), 0 2px 2px rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.12);

            ::v-deep .abc-input {
                text-align: center;
                font-size: 18px;
            }

            .input-append {
                font-weight: bold;
                font-size: 14px;
                padding-right: $spacing-sm;
            }
        }

        > .btn-right {
            margin-left: $spacing-md;
        }

        > .btn-left {
            margin-right: $spacing-md;
        }
    }
    > .chart-container {
        flex: 1;
    }
}
</style>
