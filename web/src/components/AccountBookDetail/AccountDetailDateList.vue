<template>
    <div class="account-detail-date-list">
        <div
            class="date-empty"
            v-if="Object.keys(dateList).length === 0"
        >
            해당 월에 등록된 내역이 없습니다.
        </div>
        <div
            class="date q-mb-md"
            v-for="(dateItem, date) in dateList"
            :key="date"
        >
            <div class="date-header">
                <div class="date-label">
                    {{ date }}
                </div>
                <div class="q-mr-md spend">지출: {{ dateItem.spend.toLocaleString() }}</div>
                <div class="income">수입: {{ dateItem.income.toLocaleString() }}</div>
            </div>
            <div
                class="date-item q-ml-md q-mt-sm"
                v-for="(item, idx) in dateItem.list"
                :key="idx"
            >
                <div class="item-category">
                    {{ item.category }}
                </div>
                <div class="item-memo">
                    {{ item.memo }}
                </div>
                <div
                    class="item-money"
                    :class="item.type"
                >
                    {{ item.type === 'income' ? '' : '-' }}{{ item.money }}
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps, PropType, computed, Ref } from 'vue';
import { DayAccount } from '@/types/AccountTypes';
import { apiDateToDateStr } from '@/lib/DateUtil';
import { DateAccountMap } from '@/types/AccountTypes';

const props = defineProps({
    acccountList: {
        type: Array as PropType<Array<DayAccount>>,
        required: true,
    },
});

const dateList: Ref<DateAccountMap> = computed(() => {
    const map = {};
    if (props.acccountList)
        props.acccountList.forEach((dayAccount) => {
            const dateStr = apiDateToDateStr(dayAccount.date);

            if (map[dateStr] === undefined)
                map[dateStr] = {
                    list: [],
                    income: 0,
                    spend: 0,
                };

            if (dayAccount.type === 'income') map[dateStr].income += dayAccount.money;
            else map[dateStr].spend += dayAccount.money;

            map[dateStr].list.push({
                category: dayAccount.category,
                memo: dayAccount.memo,
                type: dayAccount.type,
                money: dayAccount.money.toLocaleString(),
            });
        });

    return map;
});
</script>

<style scoped lang="scss">
.account-detail-date-list {
    display: flex;
    flex-direction: column;

    > .date-empty {
        font-size: 1.5em;
        font-weight: bold;
        margin: auto;
    }

    > .date {
        display: flex;
        flex-direction: column;

        > .date-header {
            display: flex;
            font-weight: bold;
            border-bottom: 2px solid $grey-5;

            > .date-label {
                font-size: 1.3em;
                margin-right: auto;
            }
        }
        > .date-item {
            display: flex;
            font-size: 1.1em;
            justify-content: space-between;
            border-bottom: 1px solid $grey-5;

            > .item-category {
                flex: 45%;
            }

            > .item-memo {
                flex: 35%;
            }

            > .item-money {
                flex: 20%;
                text-align: right;
            }
        }

        .spend {
            color: $pink-14;
        }

        .income {
            color: $indigo-14;
        }
    }
}
</style>
