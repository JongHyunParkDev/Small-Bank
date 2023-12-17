<template>
    <div
        class="chart-container"
        ref="chartContainer"
    ></div>
</template>

<script setup lang="ts">
import { useQuasar } from 'quasar';
import { ref, Ref, defineProps, PropType, watch, onMounted } from 'vue';

import Highcharts from 'highcharts';
import accessibility from 'highcharts/modules/accessibility';
import drilldown from 'highcharts/modules/drilldown';
import { PointLabelObject, SeriesOptionsType } from 'highcharts';
import { ChartData, DrilldownChartData, IndexMap } from '@/types/ChartTypes';
import { DayAccount } from '@/types/AccountTypes';

import { apiDateToDateStr } from '@/lib/DateUtil';

const props = defineProps({
    acccountList: {
        type: Array as PropType<Array<DayAccount>>,
        required: true,
    },
});

// base mobile
const styleOption = {
    distance: '-40%',
};
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
        type: 'pie',
    },
    credits: {
        enabled: false,
    },
    title: {
        text: undefined,
    },
    series: [
        {
            type: 'pie',
            name: 'Accounts',
            data: [] as Array<ChartData>,
        },
    ],
    accessibility: {},
    plotOptions: {},
    tooltip: {},
    drilldown: {},
};

function updateChart(newAccountList) {
    const serialDataArray: Array<ChartData> = [];
    const drilldownSerialDataArray: Array<DrilldownChartData> = [];
    const indexMap: IndexMap = {};
    let idx = 0;

    newAccountList.forEach((account) => {
        if (account.type === 'income') return;
        if (indexMap[account.category] === undefined) {
            indexMap[account.category] = idx++;
            serialDataArray.push({
                name: account.category,
                y: account.money,
                drilldown: account.category,
            });
            drilldownSerialDataArray.push({
                name: account.category,
                id: account.category,
                data: [[`${apiDateToDateStr(account.date)} (${account.memo})`, account.money]],
            });
        } else {
            serialDataArray[indexMap[account.category]].y += account.money;
            drilldownSerialDataArray[indexMap[account.category]].data.push([
                `${apiDateToDateStr(account.date)} (${account.memo})`,
                account.money,
            ]);
        }
    });

    chartOptions = {
        chart: {
            type: 'pie',
        },
        credits: {
            enabled: false,
        },
        title: {
            text: undefined,
        },
        accessibility: {
            announceNewData: {
                enabled: true,
            },
            point: {
                valueSuffix: '원',
            },
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
                        value: 10,
                    },
                    formatter: function (this: PointLabelObject): string | undefined {
                        return (
                            `<span style="color:${this.color}; text-decoration:'none'">` +
                            `Category: ${this.key} <br>` +
                            `Value: ${this.y?.toLocaleString()}원 <br>` +
                            `Percent: ${this.percentage.toFixed(2)}% <br>` +
                            '</span>'
                        );
                    },
                },
            },
        },
        tooltip: {
            headerFormat: '<span>{series.name}</span><br>',
            formatter: function (this: PointLabelObject): string | undefined {
                return (
                    `Category: <span style="color:${this.color}">${this.key}</span><br/>` +
                    `Value: <b>${this.y?.toLocaleString()}원</b><br />` +
                    `Total: <b>${this.total?.toLocaleString()}원</b>`
                );
            },
        },
        series: [
            {
                type: 'pie',
                name: 'Accounts',
                data: serialDataArray,
            },
        ],
        drilldown: {
            activeDataLabelStyle: {
                textDecoration: 'none',
            },
            series: drilldownSerialDataArray as SeriesOptionsType[],
        },
    };
    if (chart.value) chart.value.update(chartOptions);
}

watch(
    () => props.acccountList,
    (newAccountList) => {
        updateChart(newAccountList);
    },
    { deep: true, immediate: true }
);

onMounted(async () => {
    if (chartContainer.value)
        chart.value = Highcharts.chart(chartContainer.value, chartOptions, undefined);
    if (props.acccountList.length !== 0) updateChart(props.acccountList);
});
</script>

<style scoped lang="scss">
.chart-container {
    height: 100%;
}
</style>
