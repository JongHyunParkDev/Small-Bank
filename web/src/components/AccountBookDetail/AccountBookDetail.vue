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
import { useQuasar } from 'quasar'
import { ref, Ref, inject, onMounted } from 'vue';
import { PROCESS } from '@/lib/Async';
import { Api } from '@/lib/Api';
import { dateToApiDateStr, apiDateToDateStr } from '@/lib/DateUtil';
import { DayAccount } from '@/types/AccountTypes';
import Highcharts from 'highcharts';
import accessibility from 'highcharts/modules/accessibility';
import drilldown from 'highcharts/modules/drilldown';
import { ChartData, IndexMap } from '@/types/ChartTypes';

// base Mobile
const styleOption = {
    distance: '-40%',
}
const $q = useQuasar();
if ($q.platform.is.desktop) {
    styleOption.distance = '30';
}

accessibility(Highcharts);
drilldown(Highcharts);

const chartContainer: Ref<HTMLInputElement | null> = ref(null);
const chart: Ref<Highcharts.Chart | null> = ref(null);

let chartOptions: Highcharts.Options = {
    chart: {
        type: 'pie'
    },
    credits: {
        enabled: false
    },
    title: {
        text: '가계부 지출 통계',
        align: 'center'
    },
    series: [
        {
            type: 'pie',
            name: 'Accounts',
            data: [] as Array<ChartData> 
        }
    ],
    accessibility: {},
    plotOptions: {},
    tooltip:{},
    drilldown:{},
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

function updateYear(value: string | number | null) {
    // typescript 때문에 있음
    if (value === null || typeof value === 'string') return;
    if (value < 1910) value = 1910;
    if (value > 2100) value = 2100;
    nowYear.value = value;

    now.value.setFullYear(value);

    getAccounts();
}

function updateMonth(value: string | number | null) {
    // typescript 때문에 있음
    if (value === null || typeof value === 'string') return;
    if (value < 1) value = 1;
    if (value > 12) value = 12;
    nowMonth.value = value;

    now.value.setFullYear(nowYear.value);
    now.value.setMonth(value - 1);

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
        PROCESS(upProcessSpinner, downProcessSpinner, async () => {
            if (chart.value) chart.value.drillUp();

            const dayAccountList: Array<DayAccount> = await Api.get('user/accounts', {
                startDate: startDate,
                endDate: endDate,
            });

            const serialDataArray: Array<ChartData> = [];
            const drilldownSerialDataArray: Array<any> = [];
            const indexMap: IndexMap = {};
            let idx = 0;
            dayAccountList.forEach((account) => {
                if (account.type === 'income') return;
                if (indexMap[account.category] === undefined) {
                    indexMap[account.category] = idx++;
                    serialDataArray.push({
                        name: account.category,
                        y: account.money,
                        drilldown: account.category
                    })
                    drilldownSerialDataArray.push({
                        name: account.category,
                        id: account.category,
                        data: [[`${apiDateToDateStr(account.date)} (${account.memo})`, account.money]]
                    });
                }
                else {
                    serialDataArray[indexMap[account.category]].y += account.money;
                    drilldownSerialDataArray[indexMap[account.category]].data.push(
                        [`${apiDateToDateStr(account.date)} (${account.memo})`, account.money]
                    );
                }
            });

            chartOptions = {
                chart: {
                    type: 'pie'
                },
                credits: {
                    enabled: false
                },
                title: {
                    text: '가계부 지출 통계',
                    align: 'center'
                },
                accessibility: {
                    announceNewData: {
                        enabled: true
                    },
                    point: {
                        valueSuffix: '원'
                    }
                },
                plotOptions: {
                    pie: {
                        borderRadius: 5,
                        dataLabels: {
                            enabled: true,
                            distance: styleOption.distance,
                            filter: {
                                property: 'percentage',
                                operator: '>',
                                value: 10
                            },
                            formatter: function (): string | undefined {
                                if (this.y)
                                    return `<span style="color:${this.color}; text-decoration:'none'">` +
                                        `Category: ${this.key} <br>` +
                                        `Value: ${this.y.toLocaleString()}원 <br>` +
                                        `Percent: ${this.percentage.toFixed(2)}% <br>` +
                                        `</span>`;
                            }
                        }
                    }
                },
                tooltip: {
                    headerFormat: '<span>{series.name}</span><br>',
                    formatter: function (): string | undefined {
                        if (this.y && this.total)
                            return `Category: <span style="color:${this.color}">${this.key}</span><br/>` +
                                `Value: <b>${this.y.toLocaleString()}원</b><br />` +
                                `Total: <b>${this.total.toLocaleString()}원</b>`
                    }
                },
                series: [
                    {
                        type: 'pie',
                        name: 'Accounts',
                        data: serialDataArray
                    }
                ],
                drilldown: {
                    animation: false,
                    activeDataLabelStyle: {
                        textDecoration: 'none',
                    },
                    series: drilldownSerialDataArray
                },
            };

            if (chart.value) chart.value.update(chartOptions);
        });
    }
}

onMounted(() => {
    if (chartContainer.value) {
        chart.value = Highcharts.chart(chartContainer.value, chartOptions, undefined);
        getAccounts();
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
