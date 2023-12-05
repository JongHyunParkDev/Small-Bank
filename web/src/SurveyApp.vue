<template>
    <div class="survey-app">
        <div class="header">
            <div class="label">{{ surveyParams.title }}</div>
        </div>
        <div class="content">
            <div class="scroll-container">
                <div class="row-header">
                    <div class="num">구분</div>
                    <div class="content">내용</div>
                    <div class="radio-label">전혀 그렇지 않다.</div>
                    <div class="radio-label">그렇지 않다.</div>
                    <div class="radio-label">그렇다.</div>
                    <div class="radio-label">매우 그렇다.</div>
                </div>
                <div
                    v-for="(el, idx) in inputList"
                    :key="idx"
                    ref="inputListRef"
                    class="row-input"
                >
                    <div class="num">
                        {{ idx + 1 }}
                    </div>
                    <div class="content">
                        {{ el.content }}
                    </div>
                    <QRadio
                        class="radio"
                        id="radio"
                        v-for="(value, idx) in el.valueList"
                        :key="idx"
                        :val="value"
                        dense
                        v-model="el.selectInput"
                    >
                    </QRadio>
                </div>
            </div>
            <div class="submit-area">
                <QBtn
                    class="submit"
                    padding="xs lg"
                    label="제출"
                    color="primary"
                    @click="showCheck"
                />
            </div>
        </div>
        <QDialog
            class="form-dialog"
            v-model="isFormDialog"
            persistent
        >
            <QCard class="form-card">
                <QForm @submit="submit">
                    <QCardSection class="bg-primary">
                        <div class="text-h6 text-white">정보 입력</div>
                    </QCardSection>
                    <QCardSection class="q-pa-md content">
                        <QInput
                            class="q-mb-sm"
                            name="name"
                            outlined
                            stack-label
                            label="이름"
                            v-model="formInput.name"
                            :rules="[
                                (val) =>
                                    (val.length >= 2 && val.length <= 6) ||
                                    '이름은 2 ~ 6글자 사용 가능합니다.',
                            ]"
                        />
                        <QBtnToggle
                            class="q-mb-lg"
                            v-model="formInput.gender"
                            toggle-color="primary"
                            :options="[
                                { label: '남자', value: true },
                                { label: '여자', value: false },
                            ]"
                        />
                        <QInput
                            class="q-mb-lg"
                            outlined
                            stack-label
                            label="생일"
                            v-model="formInput.birth"
                        >
                            <template v-slot:append>
                                <QIcon
                                    name="event"
                                    class="cursor-pointer"
                                >
                                    <QPopupProxy
                                        cover
                                        transition-show="scale"
                                        transition-hide="scale"
                                    >
                                        <QDate
                                            v-model="formInput.birth"
                                            mask="YYYY-MM-DD"
                                        >
                                            <div class="row items-center justify-end">
                                                <QBtn
                                                    v-close-popup
                                                    label="Close"
                                                    color="primary"
                                                />
                                            </div>
                                        </QDate>
                                    </QPopupProxy>
                                </QIcon>
                            </template>
                        </QInput>
                        <QInput
                            class="q-mb-lg"
                            name="dept"
                            outlined
                            stack-label
                            label="부서"
                            v-model="formInput.dept"
                        />
                    </QCardSection>

                    <QCardSection align="right">
                        <QBtn
                            class="q-mr-sm"
                            label="취소"
                            color="negative"
                            v-close-popup
                        />
                        <QBtn
                            type="submit"
                            label="제출"
                            color="primary"
                        />
                    </QCardSection>
                </QForm>
            </QCard>
        </QDialog>
        <ProcessSpinner v-if="processCount > 0" />
    </div>
</template>

<script setup lang="ts">
import { ref, Ref, onMounted, watch } from 'vue';
import { useQuasar } from 'quasar';
import { useRoute } from 'vue-router';
import { PROCESS } from '@/lib/Async';
import { Api } from '@/lib/Api';
import ProcessSpinner from '@/components/ProcessSpinner.vue';
import { SurveyDetail } from '@/types/SurveyTypes';
import { datestrToApiDateStr } from '@/lib/DateUtil';
import { useErrorStore } from '@/stores/ErrorStore';

const route = useRoute();
const $q = useQuasar();
const errorStore = useErrorStore();

const surveyId = route.params.surveyId;

function upProcessSpinner() {
    processCount.value = 1;
}
function downProcessSpinner() {
    processCount.value = 0;
}

const processCount: Ref<number> = ref(0);

const surveyParams = ref({
    title: '',
});
const inputListRef = ref([]);
const inputList: Ref<Array<SurveyDetail>> = ref([]);
const isFormDialog = ref(false);
const formInput = ref({
    name: '',
    birth: '1995-07-07',
    dept: '',
    gender: true,
});

function submit() {
    PROCESS(upProcessSpinner, downProcessSpinner, async () => {
        const list = createSummary();
        await Api.post('anon/surveyResult', {
            surveyId: surveyId,
            name: formInput.value.name,
            birthDay: datestrToApiDateStr(formInput.value.birth),
            dept: formInput.value.dept,
            gender: formInput.value.gender,
            list: list,
        });

        isFormDialog.value = false;

        $q.notify({
            type: 'positive',
            message: '제출이 완료되었습니다.',
        });
    });
}

function showCheck() {
    if (validCheck()) {
        isFormDialog.value = true;
    }
}

function createSummary() {
    const map = {};
    const list: any[] = [];

    inputList.value.forEach((input) => {
        if (map[input.category]) {
            map[input.category] += input.selectInput;
        } else {
            map[input.category] = input.selectInput;
        }
    });

    for (const key in map) {
        list.push({
            category: key,
            value: map[key],
        });
    }

    return list;
}

function validCheck() {
    for (let i = 0; i < inputList.value.length; i++) {
        const input = inputList.value[i];
        if (input.selectInput === 0) {
            $q.notify({
                type: 'negative',
                message: '기입 되지 않은 곳이 있습니다.',
            });

            const radioContainer = inputListRef.value[i] as HTMLElement;
            if (radioContainer) {
                const radioElement = radioContainer.querySelector('#radio') as HTMLElement;
                if (radioElement) radioElement.focus();
            }
            return false;
        }
    }

    return true;
}

function init() {
    // 나중에 항목 수가 4개가 아닐 수도 있다
    const descList = [4, 3, 2, 1];
    const ascList = [1, 2, 3, 4];

    PROCESS(upProcessSpinner, downProcessSpinner, async () => {
        const result = await Api.get('anon/survey', {
            surveyId: surveyId,
        });

        surveyParams.value.title = result.title;
        result.surveyDetailDtoList.forEach((detail) => {
            inputList.value.push({
                id: detail.id,
                content: detail.content,
                category: detail.category,
                isSort: detail.isSort,
                valueList: detail.isSort ? ascList : descList,
                selectInput: 0,
            });
        });
    });
}

watch(errorStore.errors, async (newError) => {
    $q.notify({
        type: 'negative',
        message: newError[0],
        caption: '관리인에게 문의해주세요.',
    });
});

onMounted(() => {
    init();
});
</script>

<style lang="scss" scoped>
.survey-app {
    position: absolute;
    top: 0;
    bottom: 0;
    height: 100%;
    width: 100%;

    text-align: center;
    display: flex;
    flex-direction: column;

    > .header {
        flex: 0;
        background-color: $naver-bs;
        padding: $spacing-lg;
        margin-bottom: $spacing-md;
        border-bottom: 2px black solid;

        > .label {
            font-size: 2em;
            color: white;
        }
    }

    > .content {
        position: relative;
        flex: 1;
        width: 100%;
        height: 100%;

        > .scroll-container {
            position: absolute;
            top: $spacing-lg;
            left: $spacing-lg;
            right: $spacing-lg;
            bottom: 100px;
            overflow-y: auto;
            border: 1px solid black;
            border-radius: 10px;

            > .row-header {
                position: sticky;
                z-index: 2;
                top: 0px;
                border-bottom: 1px black solid;
                font-weight: bold;
                display: flex;
                background-color: $naver-bs;
                color: white;

                > .num {
                    padding: $spacing-sm;
                    border-right: 1px solid black;
                    flex: 1;
                }
                > .content {
                    padding: $spacing-sm;
                    text-align: center;
                    flex: 5;
                }
                > .radio-label {
                    border-left: 1px solid black;
                    padding: $spacing-sm 0px;
                    flex: 1;
                }
            }

            > .row-input {
                border-bottom: 1px solid black;
                display: flex;

                > .num {
                    padding: $spacing-sm;
                    border-right: 1px solid black;
                    flex: 1;
                }
                > .content {
                    padding: $spacing-sm;
                    text-align: left;
                    flex: 5;
                }
                > .radio {
                    border-left: 1px solid black;
                    flex: 1;
                    justify-content: center;
                }

                &:hover {
                    background-color: $grey-4;
                }
            }
        }

        > .submit-area {
            position: absolute;
            height: 100px;
            bottom: 0px;
            left: 0px;
            right: 0px;
            padding: $spacing-lg;
            text-align: center;
        }
    }
}

.form-dialog {
    .form-card {
        min-width: 350px;
    }
}
</style>
