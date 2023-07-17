<template>
    <div class="ab-day">
        {{ num }}
        <div 
            v-if="account"
            class="account"
        >
            <span 
                v-if="account?.income"
                class="income item"
            >
                <QIcon name="circle" />
            </span>
            <span 
                v-if="account?.spend"
                class="spend item"
            >
                <QIcon name="circle" />
            </span>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue';
import { Account } from './models';

export default defineComponent({
    name: 'ABDay',
    props: {
        num: {
            type: Number,
            required: false,
        },
        account: {
            type: Object as PropType<Account>,
            required: false
        }
    },
});
</script>

<style lang="scss" scoped>
.ab-day {
    font-size: 1em;
    font-weight: bold;
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
}
</style>
