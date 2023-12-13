<template>
    <div class="abhistory">
        <div class="scroll-container">
            <div
                v-for="(dayAccount, idx) in props.dayAccountArr"
                :key="idx"
                class="history-row"
            >
                <div class="btn item">
                    <QBtn
                        color="white"
                        text-color="black"
                        icon="edit"
                        size="sm"
                        padding="sm"
                        :ripple="false"
                        @click="modifyHistroy(idx)"
                    />
                </div>
                <div class="time item text">
                    {{ dayAccount.time }}
                </div>
                <div class="type item text">
                    {{ dayAccount.type === 'income' ? '수입' : '지출' }}
                </div>
                <div
                    class="money item text"
                    :class="dayAccount.type"
                >
                    {{ dayAccount.money.toLocaleString() }}
                </div>
                <div class="memo item text">
                    {{ dayAccount.memo }}
                    <QTooltip
                        class="bg-green"
                        anchor="top middle"
                        self="center middle"
                    >
                        {{ dayAccount.memo }}
                    </QTooltip>
                </div>
            </div>
        </div>
        <div class="summary-row">
            <div class="column">
                <div class="spend">총 지출: {{ sumSpend.toLocaleString() }}</div>
                <div class="income">총 수입: {{ sumIncome.toLocaleString() }}</div>
            </div>
            <div class="column last">
                <div :class="sumIncome > sumSpend ? 'income' : 'spend'">
                    총: {{ Math.abs(sumIncome - sumSpend).toLocaleString() }}
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineEmits, defineProps, ref, watch } from 'vue';
import { DayAccount } from '@/types/AccountTypes';

const sumSpend = ref(0);
const sumIncome = ref(0);

const props = defineProps({
    dayAccountArr: {
        type: Array<DayAccount>,
        required: true,
    },
});
const emit = defineEmits(['modify-history']);

watch(
    props.dayAccountArr,
    (newArray, oldArray) => {
        sumSpend.value = 0;
        sumIncome.value = 0;

        newArray.forEach((el) => {
            if (el.type === 'spend') sumSpend.value += el.money;
            else sumIncome.value += el.money;
        });
    },
    { deep: true }
);

function modifyHistroy(idx: number) {
    emit('modify-history', idx);
}
</script>

<style lang="scss" scoped>
.abhistory {
    $fixed-height: 70px;

    position: relative;
    display: flex;
    flex-direction: column;
    height: 100%;
    overflow-y: auto;
    margin-bottom: $fixed-height;

    > .scroll-container {
        padding: $spacing-md;

        > .history-row {
            display: flex;
            margin: $spacing-md;
            font-size: 16px;
            font-weight: bold;

            > .btn {
                flex-grow: 1;
            }

            > .item {
                text-align: center;
                flex-shrink: 0; /* 기본적으로 줄어드는 비율을 0으로 설정 */
                flex-basis: 10%; /* 기본 크기 설정을 자동으로 유지 */
                padding: $spacing-md;
            }
            > .time {
                flex-grow: 1;
            }

            > .type {
                flex-grow: 1;
            }

            > .money {
                flex-grow: 5;
            }

            > .spend {
                color: $pink-14;
            }

            > .income {
                color: $indigo-14;
            }

            > .memo {
                flex-grow: 3;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }

            > .text {
                padding: 15px 0px;
            }
        }
    }

    > .summary-row {
        position: fixed;
        bottom: 0px;
        left: 0px;
        right: 0px;
        display: flex;
        justify-content: space-around;
        border-top: 1px solid $grey-5;

        > .column {
            font-size: 16px;
            font-weight: bold;
            text-align: center;

            > .spend {
                color: $pink-14;
                margin: $spacing-sm;
            }

            > .income {
                color: $indigo-14;
                margin: $spacing-sm;
            }
        }

        > .last {
            margin: auto 0px;
            margin-right: $fixed-height;
        }
    }
}
</style>
