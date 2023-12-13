<template>
    <div class="account-detail-category-list">
        <div
            class="category q-mb-md"
            v-for="(itemList, category) in categoryList"
            :key="category"
        >
            <div class="category-label">
                {{ category }}
            </div>
            <div
                class="category-item q-ml-md q-mt-sm"
                v-for="(item, idx) in itemList"
                :key="idx"
            >
                <div class="item-date">
                    {{ item.date }}
                </div>
                <div class="item-memo">
                    {{ item.memo }}
                </div>
                <div class="item-money">
                    {{ item.type === 'income' ? '' : '-' }}{{ item.money }}
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps, PropType, computed } from 'vue';

import { DayAccount } from '@/types/AccountTypes';

import { apiDateToDateStr } from '@/lib/DateUtil';

const props = defineProps({
    acccountList: {
        type: Array as PropType<Array<DayAccount>>,
        required: true,
    },
});

const categoryList = computed(() => {
    const map = {};
    if (props.acccountList)
        props.acccountList.forEach((dayAccount) => {
            if (map[dayAccount.category] === undefined) map[dayAccount.category] = [];

            map[dayAccount.category].push({
                date: `${apiDateToDateStr(dayAccount.date)} ${dayAccount.time}`,
                memo: dayAccount.memo,
                type: dayAccount.type,
                money: dayAccount.money.toLocaleString(),
            });
        });

    return map;
});
</script>

<style scoped lang="scss">
.account-detail-category-list {
    display: flex;
    flex-direction: column;
    > .category {
        display: flex;
        flex-direction: column;

        > .category-label {
            font-size: 1.3em;
            font-weight: bold;
            border-bottom: 2px solid $grey-5;
        }
        > .category-item {
            display: flex;
            font-size: 1.1em;
            justify-content: space-between;
            border-bottom: 1px solid $grey-5;
        }
    }
}
</style>
