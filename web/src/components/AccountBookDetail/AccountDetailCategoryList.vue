<template>
    <div class="account-detail-category-list">
        <div 
            class="category-empty"
            v-if="Object.keys(categoryList).length === 0"
        >
            해당 월에 등록된 내역이 없습니다.
        </div>
        <div
            class="category q-mb-md"
            v-for="(categoryItem, category) in categoryList"
            :key="category"
        >
            <div class="category-header">
                <div class="category-label">
                    {{ category }}
                </div>
                <div class="q-mr-md spend">
                    지출: {{ categoryItem.spend.toLocaleString() }}
                </div>
                <div class="income">
                    수입: {{ categoryItem.income.toLocaleString() }}
                </div>
            </div>
            <div
                class="category-item q-ml-md q-mt-sm"
                v-for="(item, idx) in categoryItem.list"
                :key="idx"
            >
                <div class="item-date">
                    {{ item.date }}
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
import { CategoryAccountMap } from '@/types/AccountTypes';

const props = defineProps({
    acccountList: {
        type: Array as PropType<Array<DayAccount>>,
        required: true,
    },
});

const categoryList: Ref<CategoryAccountMap> = computed(() => {
    const map = {};
    if (props.acccountList)
        props.acccountList.forEach((dayAccount) => {
            if (map[dayAccount.category] === undefined) 
                map[dayAccount.category] = {
                    list: [],
                    income: 0,
                    spend: 0,
                };
            
            if (dayAccount.type === 'income')
                map[dayAccount.category].income += dayAccount.money;
            else 
                map[dayAccount.category].spend += dayAccount.money;

            map[dayAccount.category].list.push({
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

    > .category-empty {
        font-size: 1.5em;
        font-weight: bold;
        margin: auto;
    }

    > .category {
        display: flex;
        flex-direction: column;

        > .category-header {
            display: flex;
            font-weight: bold;
            border-bottom: 2px solid $grey-5;

            > .category-label {
                font-size: 1.3em;
                margin-right: auto;
            }
        }
        > .category-item {
            display: flex;
            font-size: 1.1em;
            justify-content: space-between;
            border-bottom: 1px solid $grey-5;
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
