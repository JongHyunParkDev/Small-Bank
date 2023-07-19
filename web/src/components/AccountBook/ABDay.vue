<template>
    <div class="ab-day">
        <div
            v-if="item?.num"
            :class="{'selected': selectedAbday.num === item.num}"
            class="day"
            @click="selectDay"
        >
            {{ item.num }}
            <div 
                v-if="item.account"
                class="account"
            >
                <span 
                    v-if="item.account.income"
                    class="income item"
                >
                    <QIcon name="circle" />
                </span>
                <span 
                    v-if="item.account.spend"
                    class="spend item"
                >
                    <QIcon name="circle" />
                </span>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue';
import { AccountBookDay } from './models';

export default defineComponent({
    name: 'ABDay',
    emits: ['select-day'],
    props: {
        item: {
            type: Object as PropType<AccountBookDay>,
            required: true,
        },
        selectedAbday: {
            type: Object as PropType<AccountBookDay>,
            required: false,
        },
    },
    methods: {
        selectDay() {
            this.$emit('select-day', this.item);
        }
    }
});
</script>

<style lang="scss" scoped>
.ab-day {
    font-size: 1em;
    font-weight: bold;

    > .day {
        border-radius: 10px;
        height: 100%;
        > .account {
            font-size: 0.75em;
            > .item {
                padding: 0px 3px;
            }
            > .income {
                color: $pink-14;
            }

            > .spend {
                color: $indigo-14;
            }
        }

        &:hover {
            background-color: $grey-3;
        }

        &.selected {
            background-color: $grey-5;
        }
    }
}
</style>
