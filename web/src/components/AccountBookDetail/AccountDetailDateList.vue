<template>
    <div>
        <div
            v-for="(account, idx) in acccountList"
            :key="idx"
        >
            {{ account.category }}
        </div>
    </div>
</template>

<script setup lang="ts">
import { useQuasar } from 'quasar';
import { ref, Ref, defineProps, PropType, watch, onMounted, nextTick } from 'vue';

import Highcharts from 'highcharts';
import accessibility from 'highcharts/modules/accessibility';
import drilldown from 'highcharts/modules/drilldown';
import { PointLabelObject } from 'highcharts';
import { ChartData, IndexMap } from '@/types/ChartTypes';
import { DayAccount } from '@/types/AccountTypes';

import { apiDateToDateStr } from '@/lib/DateUtil';

const props = defineProps({
    acccountList: {
        type: Array as PropType<Array<DayAccount>>,
        required: true,
    },
});

console.log(props.acccountList);
</script>

<style scoped lang="scss"></style>
