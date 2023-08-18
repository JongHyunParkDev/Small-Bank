<template>
    <div class="abhistory">
        <div class="scroll-container">
            <div
                v-for="(dayAccount, idx) in dayAccountArr"
                :key="idx"
                class="history-row"
            >
                <!-- TODO API 구현 후 변경 가능성 많음 -->
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
                        class="bg-indigo"
                        anchor="top middle"
                        self="center middle"
                    >
                        {{ dayAccount.memo }}
                    </QTooltip>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref, Ref, PropType, computed } from 'vue';

export default defineComponent({
    name: 'ABHistroy',
    emits: ['modify-history'],
    props: {
        dayAccountArr: {
            type: Array as PropType<Array<any>>
        }
    },
    setup() {

        return { };
    },
    methods: {
        modifyHistroy(idx : number) {
            console.log(idx);
            this.$emit('modify-history', idx);
        }
    }
});
</script>

<style lang="scss" scoped>
.abhistory {
    display: flex;
    flex-direction: column;
    height: 100%;
    overflow-y: auto;

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
}
</style>
