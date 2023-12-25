<template>
    <div class="ab-day">
        <div
            v-if="props.item.num"
            :class="{ selected: props.selectedAbday?.num === props.item.num }"
            class="day"
            @click="selectDay"
        >
            {{ props.item.num }}
            <div
                v-if="props.accountMap"
                class="account"
            >
                <span
                    v-if="props.accountMap?.income"
                    class="income item"
                >
                    <QIcon name="circle" />
                </span>
                <span
                    v-if="props.accountMap?.spend"
                    class="spend item"
                >
                    <QIcon name="circle" />
                </span>
            </div>
            <div
                v-else
                class="account"
            >
                <span class="item"> </span>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { PropType, defineEmits, defineProps } from 'vue';
import { AccountBookDay } from '@/types/AccountTypes';

const props = defineProps({
    item: {
        type: Object as PropType<AccountBookDay>,
        required: true,
    },
    selectedAbday: {
        type: Object as PropType<AccountBookDay>,
        required: false,
    },
    accountMap: {
        type: Object,
        required: false,
    },
});
const emit = defineEmits(['select-day']);

function selectDay() {
    emit('select-day', props.item);
}
</script>

<style lang="scss" scoped>
.ab-day {
    > .day {
        border-radius: $spacing-md;
        height: 100%;
        > .account {
            font-size: 0.75em;
            > .item {
                padding: 0px $spacing-tn;
            }
            > .spend {
                color: $pink-14;
            }

            > .income {
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
