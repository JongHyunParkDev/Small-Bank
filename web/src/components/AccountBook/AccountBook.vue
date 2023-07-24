<template>
    <div class="accountBook">
        <ABCalendar
            class="calendar"
            @selectDay="selectDay($event)"
        />
        <div class="divider" />
        <ABHistory
            class="history"
            :day-account-arr="dayAccountArr"
            @deleteHistory="deleteHistory"
        />
        <!-- <QFab
            class="fab"
            color="primary"
            icon="keyboard_arrow_up"
            direction="up">
            <QFabAction
                color="primary"
                icon="list"
            />
            <QFabAction
                color="primary"
                icon="add"
            />
        </QFab> -->
        <QBtn
            class="fab"
            round
            color="primary"
            icon="add"
            @click="isAddDialog = true"
        />
        <QDialog
            class="abhistory-add-dialog"
            v-model="isAddDialog"
            persistent
        >
            <QCard>
                <QCardSection class="row items-center">
                    <QToolbar>
                        <QToolbarTitle>
                            내역 추가하기
                        </QToolbarTitle>
                    </QToolbar>
                    <diV class="dialog-content">
                        <QInput
                            outlined
                            stack-label
                            label="시간"
                            v-model="time"
                            mask="time"
                            :rules="['time']"
                        >
                            <template v-slot:append>
                            <QIcon name="access_time" class="cursor-pointer">
                                <QPopupProxy cover transition-show="scale" transition-hide="scale">
                                    <QTime v-model="time">
                                        <div class="row items-center justify-end">
                                            <QBtn v-close-popup label="Close" color="primary" flat />
                                        </div>
                                    </QTime>
                                </QPopupProxy>
                            </QIcon>
                            </template>
                        </QInput>
                        <QInput
                            class="input"
                            outlined
                            stack-label
                            label="카테고리"
                            v-model="category"
                        />
                        <QInput
                            class="input"
                            outlined
                            stack-label
                            label="금액"
                            type="number"
                            v-model="money"
                        />
                        <QInput
                            class="input"
                            outlined
                            stack-label
                            label="메모"
                            v-model="memo"
                            type="textarea"
                            :input-style="{resize: 'none'}"
                        />
                    </diV>

                </QCardSection>

                <QCardActions align="right">
                    <QBtn flat label="취소" color="primary" v-close-popup />
                    <QBtn flat label="추가" color="primary" @click="addHistory" v-close-popup />
                </QCardActions>
            </QCard>
        </QDialog>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref, Ref } from 'vue';
import ABCalendar from 'components/AccountBook/ABCalendar.vue';
import ABHistory from 'components/AccountBook/ABHistory.vue';

//TODO 거래 내역은 추후에 api 로 변경해야한다.
// id, timestamp, text, money, category, type
const dayAccountArr: Ref<Array<any>> = ref([]);
const isAddDialog = ref(false);

const time: Ref<string> = ref('00:00');

function selectDay(a) {
    console.log(a);
    const spendSize = Math.floor(Math.random() * 7) + 20;
    const incomeSize = Math.floor(Math.random() * 30) < 1 ? 0 : 1;

    dayAccountArr.value = [];

    for (let i = 0; i < spendSize; i++) {
        dayAccountArr.value.push({
            time: '00:00', // DateUtil.getTime
            text: '지출 ' + i,
            money: (Math.floor(Math.random() * 10) + 1) * 1000,
            type: 'spend'
        })
    }

    for (let i = 0; i < incomeSize; i++) {
        dayAccountArr.value.push({
            time: '00:00', // DateUtil.getTime
            text: '소득 ' + i,
            money: Math.floor(Math.random() * 5) * 1000,
            type: 'income'
        })
    }

}

export default defineComponent({
    name: 'AccountBook',
    components: {
        ABCalendar,
        ABHistory
    },
    setup() {

        return {
            selectDay,
            dayAccountArr,
            isAddDialog,
            time
        };
    },
    methods: {
        deleteHistory(idx) {
            //TODO API 구현
            dayAccountArr.value.splice(idx, 1);
        },
        addHistory() {
            console.log('ㅇㅇ');
            // dayAccountArr.value.push({});
        }
    }
});
</script>

<style lang="scss" scoped>
.accountBook {
    position: absolute;
    top: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;

    > .calendar {
        position: relative;
    }

    > .divider {
        border-top: 0.5px $grey-5 solid;
    }

    > .history {
        flex: 1;
        position: relative;
    }

    > .fab {
        position: fixed;
        bottom: 20px;
        right: 20px;
    }
}

.abhistory-add-dialog {
    .dialog-content {
        display: flex;
        flex-direction: column;
        width: 100%;

        > .input {
            margin-bottom: 10px;
        }
    }
}
</style>
