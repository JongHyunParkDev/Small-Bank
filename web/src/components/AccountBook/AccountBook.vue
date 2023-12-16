<template>
    <div class="accountBook">
        <ABCalendar
            class="calendar"
            :day-account-arr="dayAccountArr"
            @update-calendar="updateCalendar($event)"
            @select-day="selectDay($event)"
        />
        <div class="divider" />
        <ABHistory
            class="history"
            :day-account-arr="sortAccountArr"
            @modify-history="showModifyDialog"
        />
        <div class="fab-area">
            <QBtn
                class="fab"
                round
                icon="add"
                :disable="selectedDay === undefined"
                @click="isAddDialog = true"
            />
            <QTooltip
                v-if="selectedDay === undefined"
                class="bg-green"
            >
                일을 선택해주세요
            </QTooltip>
        </div>

        <QDialog
            class="form-dialog"
            v-model="isAddDialog"
            persistent
        >
            <QCard class="form-card">
                <QForm @submit="addHistory">
                    <QCardSection class="bg-primary">
                        <div class="text-h6 text-white">내역 추가하기</div>
                    </QCardSection>
                    <QCardSection class="q-pa-md content">
                        <QInput
                            outlined
                            stack-label
                            label="시간"
                            v-model="time"
                            mask="time"
                            :rules="['time' || '시간을 입력해주세요.']"
                        >
                            <template v-slot:append>
                                <QIcon
                                    name="access_time"
                                    class="cursor-pointer"
                                >
                                    <QPopupProxy
                                        cover
                                        transition-show="scale"
                                        transition-hide="scale"
                                    >
                                        <QTime v-model="time">
                                            <div class="row items-center justify-end">
                                                <QBtn
                                                    v-close-popup
                                                    label="Close"
                                                    color="primary"
                                                    flat
                                                />
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
                            :rules="[(val) => val !== '' || '카테고리를 입력해주세요.']"
                        />
                        <QBtnToggle
                            v-model="type"
                            toggle-color="primary"
                            :options="[
                                { label: '지출', value: 'spend' },
                                { label: '수입', value: 'income' },
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
                            :input-style="{ resize: 'none' }"
                            :rules="[(val) => val !== '' || '메모를 입력해주세요.']"
                        />
                    </QCardSection>
                    <QCardActions align="right">
                        <QBtn
                            flat
                            padding="xs lg"
                            label="취소"
                            color="primary"
                            @click="clearDialogForm"
                            v-close-popup
                        />
                        <QBtn
                            padding="xs lg"
                            type="submit"
                            label="추가"
                            color="primary"
                        />
                    </QCardActions>
                </QForm>
            </QCard>
        </QDialog>
        <QDialog
            class="form-dialog"
            v-model="isModifyDialog"
            persistent
        >
            <QCard class="form-card">
                <QForm @submit="modifyHistory">
                    <QCardSection class="bg-primary">
                        <div class="text-h6 text-white">내역 변경하기</div>
                    </QCardSection>
                    <QCardSection class="q-pa-md content">
                        <QInput
                            outlined
                            stack-label
                            label="시간"
                            v-model="time"
                            mask="time"
                            :rules="['time' || '시간을 입력해주세요.']"
                        >
                            <template v-slot:append>
                                <QIcon
                                    name="access_time"
                                    class="cursor-pointer"
                                >
                                    <QPopupProxy
                                        cover
                                        transition-show="scale"
                                        transition-hide="scale"
                                    >
                                        <QTime v-model="time">
                                            <div class="row items-center justify-end">
                                                <QBtn
                                                    v-close-popup
                                                    label="Close"
                                                    color="primary"
                                                    flat
                                                />
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
                            :rules="[(val) => val !== '' || '카테고리를 입력해주세요.']"
                        />
                        <QBtnToggle
                            v-model="type"
                            toggle-color="primary"
                            :options="[
                                { label: '지출', value: 'spend' },
                                { label: '수입', value: 'income' },
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
                            :input-style="{ resize: 'none' }"
                            :rules="[(val) => val !== '' || '메모를 입력해주세요.']"
                        />
                    </QCardSection>
                    <QCardActions align="between">
                        <QBtn
                            flat
                            padding="xs lg"
                            label="취소"
                            color="primary"
                            @click="clearDialogForm"
                            v-close-popup
                        />
                        <QBtn
                            padding="xs lg"
                            label="삭제"
                            color="negative"
                            @click="deleteHistory()"
                        />
                        <QBtn
                            padding="xs lg"
                            type="submit"
                            label="변경"
                            color="primary"
                        />
                    </QCardActions>
                </QForm>
            </QCard>
        </QDialog>
    </div>
</template>

<script setup lang="ts">
// TODO 모든 vue 파일을 Composition 으로 변경한다.
import { ref, Ref, computed, inject } from 'vue';
import { compare } from '@/lib/StrUtil';
import { PROCESS } from '@/lib/Async';
import { Api } from '@/lib/Api';
import { dateToApiDateStr } from '@/lib/DateUtil';
import { DayAccount } from '@/types/AccountTypes';
import ABCalendar from 'components/AccountBook/ABCalendar.vue';
import ABHistory from 'components/AccountBook/ABHistory.vue';

const upProcessSpinner = inject<() => void>('upProcessSpinner');
const downProcessSpinner = inject<() => void>('downProcessSpinner');

const dayAccountArr: Ref<Array<DayAccount>> = ref([]);
const isAddDialog = ref(false);
const isModifyDialog = ref(false);

const time: Ref<string> = ref('00:00');
const category = ref('');
const memo = ref('');
const money = ref(0);
const selectedDay: Ref<string | undefined> = ref(undefined);
const type = ref('spend');
const selectedIdx = ref(0);

const sortAccountArr = computed(() => {
    return dayAccountArr.value
        .filter((account) => account.date === selectedDay.value)
        .sort((a, b) => compare(a.time, b.time));
});

function selectDay(day: string) {
    selectedDay.value = day;
}

function updateCalendar({ year, month }: { year: number; month: number }) {
    const lastDay = new Date(year, month, 0).getDate();

    selectedDay.value = undefined;
    const startDate = dateToApiDateStr(year, month, 1);
    const endDate = dateToApiDateStr(year, month, lastDay);

    // 사실 ProcesSpinner 가 없는 경우는 없다. typescript 을 위해서...
    if (upProcessSpinner && downProcessSpinner) {
        PROCESS(upProcessSpinner, downProcessSpinner, async () => {
            const dayAccountList: Array<DayAccount> = await Api.get('user/account', {
                startDate: startDate,
                endDate: endDate,
            });

            dayAccountArr.value = [];

            dayAccountList.forEach((dayAccount) => {
                dayAccountArr.value.push(dayAccount);
            });
        });
    }
}

function clearDialogForm() {
    time.value = '00:00';
    category.value = '';
    type.value = 'spend';
    memo.value = '';
    money.value = 0;
}

function showModifyDialog(idx: number) {
    selectedIdx.value = idx;
    const account = sortAccountArr.value[idx];

    time.value = account.time;
    category.value = account.category;
    type.value = account.type;
    memo.value = account.memo;
    money.value = account.money;

    isModifyDialog.value = true;
}

function addHistory() {
    // 사실 ProcesSpinner 가 없는 경우는 없다. typescript 을 위해서...
    if (upProcessSpinner && downProcessSpinner) {
        isAddDialog.value = false;

        PROCESS(upProcessSpinner, downProcessSpinner, async () => {
            const dayAccount = await Api.post('user/account', {
                date: selectedDay.value,
                time: time.value,
                category: category.value,
                memo: memo.value,
                money: +money.value,
                type: type.value,
            });

            dayAccountArr.value.push({
                id: dayAccount.id,
                date: dayAccount.date,
                time: dayAccount.time,
                category: dayAccount.category,
                memo: dayAccount.memo,
                money: +dayAccount.money,
                type: dayAccount.type,
            });

            clearDialogForm();
        });
    }
}

function deleteHistory() {
    // 사실 ProcesSpinner 가 없는 경우는 없다. typescript 을 위해서...
    if (upProcessSpinner && downProcessSpinner) {
        isModifyDialog.value = false;

        PROCESS(upProcessSpinner, downProcessSpinner, async () => {
            const account = sortAccountArr.value[selectedIdx.value];

            await Api.delete('user/account', {
                id: account.id,
            });

            dayAccountArr.value = dayAccountArr.value.filter((item) => item !== account);

            clearDialogForm();
        });
    }
}

function modifyHistory() {
    // 사실 ProcesSpinner 가 없는 경우는 없다. typescript 을 위해서...
    if (upProcessSpinner && downProcessSpinner) {
        isModifyDialog.value = false;

        PROCESS(upProcessSpinner, downProcessSpinner, async () => {
            const account = sortAccountArr.value[selectedIdx.value];

            await Api.put('user/account', {
                id: account.id,
                date: account.date,
                time: time.value,
                category: category.value,
                memo: memo.value,
                money: +money.value,
                type: type.value,
            });

            account.time = time.value;
            account.category = category.value;
            account.memo = memo.value;
            account.money = +money.value;
            account.type = type.value;

            clearDialogForm();
        });
    }
}
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

    > .fab-area {
        position: fixed;
        bottom: 20px;
        right: 20px;
        > .fab {
            background-color: $naver-bs;
            color: white;
        }
    }
}

.form-dialog {
    .form-card {
        min-width: 350px;
    }
}
</style>
