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
            <!-- filled
                dense -->
            <QInput
                class="abc-outer"
                input-class="abc-input"
                dense
                filled
                v-model.number="nowYear"
                @update:model-value="updateYear"
            >
                <template v-slot:append>
                    <span class="input-append"> 년 </span>
                </template>
            </QInput>
            <QInput
                class="abc-outer"
                input-class="abc-input"
                dense
                filled
                v-model.number="nowMonth"
                @update:model-value="updateMonth"
            >
                <template v-slot:append>
                    <span class="input-append"> 월 </span>
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
        <QTabPanels
            class="tab-panels"
            v-model="selectedTab"
            animated
            transition-prev="jump-up"
            transition-next="jump-up"
        >
            <QTabPanel name="chart">
                <AccountDetailChart :acccount-list="acccountList" />
            </QTabPanel>
            <QTabPanel name="categoryList">
                <AccountDetailCategoryList :acccount-list="acccountList" />
            </QTabPanel>
            <QTabPanel name="dateList">
                <AccountDetailDateList :acccount-list="acccountList" />
            </QTabPanel>
        </QTabPanels>
        <QTabs
            v-model="selectedTab"
            class="tabs"
            indicator-color="transparent"
            outside-arrows
            dense
        >
            <QTab
                name="chart"
                icon="pie_chart"
                label="Chart"
            ></QTab>
            <QTab
                name="categoryList"
                icon="category"
                label="category List"
            ></QTab>
            <QTab
                name="dateList"
                icon="calendar_month"
                label="date List"
            ></QTab>
        </QTabs>
    </div>
</template>

<script setup lang="ts">
import { ref, Ref, inject, onMounted } from 'vue';
import { PROCESS } from '@/lib/Async';
import { Api } from '@/lib/Api';
import { dateToApiDateStr } from '@/lib/DateUtil';
import { DayAccount } from '@/types/AccountTypes';
import { compare } from '@/lib/StrUtil';
import AccountDetailCategoryList from '@/components/AccountBookDetail/AccountDetailCategoryList.vue';
import AccountDetailChart from '@/components/AccountBookDetail/AccountDetailChart.vue';
import AccountDetailDateList from '@/components/AccountBookDetail/AccountDetailDateList.vue';

const upProcessSpinner = inject<() => void>('upProcessSpinner');
const downProcessSpinner = inject<() => void>('downProcessSpinner');

const selectedTab = ref('chart');

const acccountList: Ref<Array<DayAccount>> = ref([]);

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
            const resultAccountList: Array<DayAccount> = await Api.get('user/account', {
                startDate: startDate,
                endDate: endDate,
            });

            const sortAccountList = resultAccountList.sort((a, b) => compare(a.date, b.date));

            acccountList.value = sortAccountList;
        });
    }
}

onMounted(() => {
    getAccounts();
});
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

    > .header {
        display: flex;
        margin: $spacing-sm auto;

        > .abc-outer {
            max-width: 100px;
            margin: 0px $spacing-sm;
            border-radius: $spacing-sm;
            box-shadow: 0 1px $spacing-sm rgba(0, 0, 0, 0.2), 0 2px 2px rgba(0, 0, 0, 0.14),
                0 3px 1px -2px rgba(0, 0, 0, 0.12);

            &:deep(.abc-input) {
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

    > .btn-toggle {
        margin: 0px auto;
    }

    > .tab-panels {
        flex: 1;
    }
    > .tabs {
        background-color: $naver-bs;
        color: white;
    }
}
</style>
