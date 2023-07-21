<template>
    <div class="abhistory">
        <div class="scroll-container">
            <div
                v-for="(dayAccount, idx) in dayAccountArr"
                :key="idx"
                class="history-row"
            >
            <!-- TODO API 구현 후 변경 가능성 많음 -->
                <div class="time item">
                    {{ dayAccount.time }}
                </div>
                <div class="type item">
                    {{ dayAccount.type === 'income' ? '수입' : '지출' }}
                </div>
                <div
                    class="money item"
                    :class="dayAccount.type"
                >
                    {{ dayAccount.money }}
                </div>
                <div class="text item">
                    {{ dayAccount.text }}
                    <QTooltip
                        class="bg-indigo"
                        anchor="top middle"
                    >
                        {{ dayAccount.text }}
                    </QTooltip>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref, Ref, PropType } from 'vue';

export default defineComponent({
    name: 'ABHistroy',
    emits: ['delete-history'],
    props: {
        dayAccountArr: {
            type: Array as PropType<Array<any>>
        }
    },
    setup() {

        return { };
    },
});
</script>

<style lang="scss" scoped>
.abhistory {
    display: flex;
    flex-direction: column;
    height: 100%;
    overflow-y: auto;

    > .scroll-container {
        padding: 10px;

        > .history-row {
            display: flex;
            margin: 5px;
            font-size: 16px;
            font-weight: bold;

            > .item {
                text-align: center;
                flex-shrink: 0; /* 기본적으로 줄어드는 비율을 0으로 설정 */
                flex-basis: 10%; /* 기본 크기 설정을 자동으로 유지 */
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

            > .text {
                flex-grow: 3;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }

        }
    }
}
</style>
