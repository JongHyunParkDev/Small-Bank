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
        <div class="chart-container" ref="chartContainer">
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, Ref, inject, onMounted } from 'vue';
import { process } from '@/lib/Async';
import { Api } from '@/lib/Api';
import { dateToApiDateStr } from '@/lib/DateUtil';
import { DayAccount } from '@/types/AccountTypes';
import Highcharts from 'highcharts';
import accessibility from 'highcharts/modules/accessibility';
import drilldown from 'highcharts/modules/drilldown';

accessibility(Highcharts);
drilldown(Highcharts);

const chartContainer = ref<HTMLInputElement | null>(null);
const chart = ref(null);
const chartOptions = {
    chart: {
        type: 'pie'
    },
    title: {
        text: '가계부 통계',
        align: 'center'
    },
    accessibility: {
        announceNewData: {
            enabled: true
        },
        point: {
            valueSuffix: '%'
        }
    },
    plotOptions: {
        series: {
            borderRadius: 5,
            dataLabels: {
                enabled: true,
                format: '{point.name}: {point.y:.1f}%'
            }
        }
    },

    tooltip: {
        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
    },

    series: [
        {
            name: 'Browsers',
            colorByPoint: true,
            data: [
                {
                    name: 'Chrome',
                    y: 61.04,
                    drilldown: 'Chrome'
                },
                {
                    name: 'Safari',
                    y: 9.47,
                    drilldown: 'Safari'
                },
                {
                    name: 'Edge',
                    y: 9.32,
                    drilldown: 'Edge'
                },
                {
                    name: 'Firefox',
                    y: 8.15,
                    drilldown: 'Firefox'
                },
                {
                    name: 'Other',
                    y: 11.02,
                    drilldown: null
                }
            ]
        }
    ],
    drilldown: {
        series: [
            {
                name: 'Chrome',
                id: 'Chrome',
                data: [
                    [
                        'v97.0',
                        36.89
                    ],
                    [
                        'v96.0',
                        18.16
                    ],
                    [
                        'v95.0',
                        0.54
                    ],
                    [
                        'v94.0',
                        0.7
                    ],
                    [
                        'v93.0',
                        0.8
                    ],
                    [
                        'v92.0',
                        0.41
                    ],
                    [
                        'v91.0',
                        0.31
                    ],
                    [
                        'v90.0',
                        0.13
                    ],
                    [
                        'v89.0',
                        0.14
                    ],
                    [
                        'v88.0',
                        0.1
                    ],
                    [
                        'v87.0',
                        0.35
                    ],
                    [
                        'v86.0',
                        0.17
                    ],
                    [
                        'v85.0',
                        0.18
                    ],
                    [
                        'v84.0',
                        0.17
                    ],
                    [
                        'v83.0',
                        0.21
                    ],
                    [
                        'v81.0',
                        0.1
                    ],
                    [
                        'v80.0',
                        0.16
                    ],
                    [
                        'v79.0',
                        0.43
                    ],
                    [
                        'v78.0',
                        0.11
                    ],
                    [
                        'v76.0',
                        0.16
                    ],
                    [
                        'v75.0',
                        0.15
                    ],
                    [
                        'v72.0',
                        0.14
                    ],
                    [
                        'v70.0',
                        0.11
                    ],
                    [
                        'v69.0',
                        0.13
                    ],
                    [
                        'v56.0',
                        0.12
                    ],
                    [
                        'v49.0',
                        0.17
                    ]
                ]
            },
            {
                name: 'Safari',
                id: 'Safari',
                data: [
                    [
                        'v15.3',
                        0.1
                    ],
                    [
                        'v15.2',
                        2.01
                    ],
                    [
                        'v15.1',
                        2.29
                    ],
                    [
                        'v15.0',
                        0.49
                    ],
                    [
                        'v14.1',
                        2.48
                    ],
                    [
                        'v14.0',
                        0.64
                    ],
                    [
                        'v13.1',
                        1.17
                    ],
                    [
                        'v13.0',
                        0.13
                    ],
                    [
                        'v12.1',
                        0.16
                    ]
                ]
            },
            {
                name: 'Edge',
                id: 'Edge',
                data: [
                    [
                        'v97',
                        6.62
                    ],
                    [
                        'v96',
                        2.55
                    ],
                    [
                        'v95',
                        0.15
                    ]
                ]
            },
            {
                name: 'Firefox',
                id: 'Firefox',
                data: [
                    [
                        'v96.0',
                        4.17
                    ],
                    [
                        'v95.0',
                        3.33
                    ],
                    [
                        'v94.0',
                        0.11
                    ],
                    [
                        'v91.0',
                        0.23
                    ],
                    [
                        'v78.0',
                        0.16
                    ],
                    [
                        'v52.0',
                        0.15
                    ]
                ]
            }
        ]
    }
};

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
    if (chartContainer.value) {
        chart.value = Highcharts.chart(chartContainer.value, chartOptions);
    }
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
        margin: 5%;
    }
}
</style>
