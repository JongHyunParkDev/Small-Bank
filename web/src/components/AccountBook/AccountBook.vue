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
            :disable="sel"
            @click="isAddDialog = true"
        />
        <QDialog
            class="abhistory-add-dialog"
            v-model="isAddDialog"
            persistent
        >
            <QCard class="abhistory-card">
                <QCardSection class="row items-center">
                    <QToolbar>
                        <QToolbarTitle>
                            내역 추가하기
                        </QToolbarTitle>
                    </QToolbar>
                    <QForm class="dialog-content">
                        <QInput
                            outlined
                            stack-label
                            label="시간"
                            v-model="time"
                            mask="time"
                            :rules="['time' || '시간을 입력해주세요.']"
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
                            name="category"
                            outlined
                            stack-label
                            label="카테고리"
                            v-model="category"
                            :rules="[(val) => (val !== '') || '카테고리를 입력해주세요.']"
                        />
                        <QBtnToggle
                            v-model="type"
                            toggle-color="primary"
                            :options="[
                                {label: '지출', value: 'spend'},
                                {label: '수입', value: 'income'}
                            ]"
                        />
                        <QInput
                            name="money"
                            outlined
                            stack-label
                            label="금액"
                            type="number"
                            v-model="money"
                            :rules="[(val) => (val !== '' && val != '0') || '금액을 입력해주세요.']"
                        />
                        <QInput
                            name="memo"
                            outlined
                            stack-label
                            label="메모"
                            v-model="memo"
                            type="textarea"
                            :input-style="{resize: 'none'}"
                            :rules="[(val) => (val !== '') || '메모를 입력해주세요.']"
                        />
                    </QForm>
                </QCardSection>
                <QCardActions align="right">
                    <QBtn
                        flat
                        label="취소"
                        color="primary"
                        @click="clear"
                        v-close-popup
                    />
                    <QBtn
                        label="추가"
                        color="primary"
                        @click="addHistory"
                        v-close-popup
                    />
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
const category = ref('');
const memo = ref('');
const money = ref(0);
const selectedDay = ref('');
const type = ref('spend');

function selectDay(day) {
    selectedDay.value = day;

    dayAccountArr.value = [];

    // TODO API getList
}

function clear() {
    time.value ='00:00';
    category.value = '';
    type.value = 'spend';
    memo.value = '';
    money.value = 0;
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
            clear,

            selectedDay,
            time,
            category,
            type,
            money,
            memo
        };
    },
    methods: {
        deleteHistory(idx) {
            //TODO API 구현
            dayAccountArr.value.splice(idx, 1);
        },
        addHistory() {
            dayAccountArr.value.push({
                time: time.value,
                text: memo.value,
                money: +money.value,
                type: type.value,
            });
            clear();
        },

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
    .abhistory-card {
        max-width: 350px;
        min-width: 350px;
    }
    .dialog-content {
        display: flex;
        flex-direction: column;
        width: 100%;
    }
}
</style>
