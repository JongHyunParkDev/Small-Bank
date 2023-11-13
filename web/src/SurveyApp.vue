<template>
    <div class="survey-app">
        <div class="header">
            <div class="label">직무 스트레스 측정</div>
        </div>
        <div class="content">
            <div class="scroll-container">
                <div class="row-header">
                    <div class="num">구분</div>
                    <div class="text">내용</div>
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
                    <div class="text">
                        {{ el.text }}
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
            class="is-form-dialog"
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
    </div>
</template>

<script setup lang="ts">
import { ref, Ref, onMounted } from 'vue';
import { useQuasar } from 'quasar';
import { useRoute } from 'vue-router';

const route = useRoute();
const $q = useQuasar();

const inputListRef = ref([]);
const inputList = ref([
    {
        text: '근무장소가 깨끗하고 쾌적하다.',
        category: '물리환경',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '내일은 위험하며 사고를 당할 가능성이 있다.',
        category: '물리환경',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '내업무는 불편한자세로 오랫동안 일을 해야한다.',
        category: '물리환경',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    // 직무 요구
    {
        text: '나는 일이 많아 항상 시간에 쫓기며 일한다.',
        category: '직무요구',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '현재 하던일을 끝내기 전에 다른 일을 하도록 지시받는다.',
        category: '직무요구',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '업무량이 현저하게 증가하였다.',
        category: '직무요구',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '나는 동료나 부하직원을 돌보고 책임져야 할 부담을 안고있다.',
        category: '직무요구',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '내 업무는 장시간 동안 집중력이 요구된다.',
        category: '직무요구',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '업무수행중에 충분한 휴식(짬)이 주어진다',
        category: '직무요구',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '일이 많아서 직장과 가정에 다 잘하기가 힘들다.',
        category: '직무요구',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '여러가지 일을 동시에 해야 한다.',
        category: '직무요구',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    // 직무 자율
    {
        text: '내 업무는 창의력을 필요로 한다.',
        category: '직무자율',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '업무관련 사항(업무의 일정, 업무량, 회의시간등)이 예고없이 갑작스럽게 정해지거나 바뀐다.',
        category: '직무자율',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '내 업무를 수행하기 위해서는 높은 수준의 기술이나 지식이 필요하다.',
        category: '직무자율',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '작업시간, 업무수행과정에서 나에게 결정할 권한이 주어지며 영향력을 행사할 수 있다.',
        category: '직무자율',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '나의 업무량과 작업스케줄을 스스로 조절할 수 있다.',
        category: '직무자율',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },

    {
        text: '나의 상사는 업무를 완료하는데 도움을 준다.',
        category: '관계갈등',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '나의 동료는 업무를 완료하는데 도움을 준다.',
        category: '관계갈등',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '직장에서 내가 힘들때 내가 힘들다는 것을 알아주고 이해해 주는 사람이 있다.',
        category: '관계갈등',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '직장생활의 고충을 함께 나눌 동료가 있다.',
        category: '관계갈등',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },

    {
        text: '지금의 직장을 옮겨도 나에게 적합한 새로운 일을 쉽게 찾을 수 있다.',
        category: '직무불안정',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '현재의 직장을 그만두더라도 현재 수준만큼의 직업(직장)을 쉽게 구할수 있다.',
        category: '직무불안정',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '직장사정이 불안하여 미래가 불확실한다.',
        category: '직무불안정',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '나의 작업은 실직하거나 해고당할 염려가 없다.',
        category: '직무불안정',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '앞으로 2년동안 현재의 내 직업을 잃을 가능성이 있다.',
        category: '직무불안정',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '나의 근무조건이나 상황에 바람직하지 못한 변화(예:구조조정)가 있었거나 있을 것으로 예상된다.',
        category: '직무불안정',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },

    {
        text: '우리 직장은 근무평가,인사제도(승진,부서배치등)가 공정하고 합리적이다.',
        category: '조직체계',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '업무수행에 필요한 인원, 공간, 시설, 장비, 훈련, 등의 지원이 잘 이루어지고 있다.',
        category: '조직체계',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '우리 부서와 타 부서간에는 마찰이 없고 업무 협조가 잘 이루어진다.',
        category: '조직체계',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '근로자, 간부, 경영주 모두가 직장을 위해 한마음으로 일한다.',
        category: '조직체계',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '일에 대한 나의 생각을 반영할 수 있는 기회와 통로가 있다.',
        category: '조직체계',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '나의 경력개발과 승진은 무난히 잘 될 것으로 예상한다.',
        category: '조직체계',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '내 현재 직위는 나의 교육 및 경력에 비추어볼때 적절하다.',
        category: '조직체계',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },

    {
        text: '나의 직업은 내가 평소 기대했던 것에 미치지 못한다.',
        category: '보상부적절',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '나의 모든 노력과 업적을 고려할 때 내 봉급/수입은 적절하다.',
        category: '보상부적절',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '나의 모든 노력과 업적을 고려할 때, 나는 직장에서 제대로 존중과 신임을 받고 있다.',
        category: '보상부적절',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '나는 지금하는일에 흥미를 느낀다.',
        category: '보상부적절',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '내 사정이 앞으로 더 좋아질 것을 생각하면 힘든 줄 모르고 일하게 된다.',
        category: '보상부적절',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },
    {
        text: '나의 능력을 개발하고 발휘할 수 있는 기회가 주어진다.',
        category: '보상부적절',
        valueList: [4, 3, 2, 1],
        selectInput: 0,
    },

    {
        text: '회식자리가 불편하다.',
        category: '직장문화',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '나는 기준이나 일관성이 없는 상태로 업무지시를 받는다.',
        category: '직장문화',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '직장의 분위기가 권위적이고 수직적이다.',
        category: '직장문화',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
    {
        text: '남성,여성이라는 성적인 차이 때문에 불이익을 받는다.',
        category: '직장문화',
        valueList: [1, 2, 3, 4],
        selectInput: 0,
    },
]);
const isFormDialog = ref(false);
const formInput = ref({
    name: '',
    birth: '1995-07-07',
    dept: '',
    gender: true,
});

function submit() {
    // const list = createSummary();
    // api.post('anon/surveyResult', {inputForm, list})

    isFormDialog.value = false;

    $q.notify({
        type: 'positive',
        message: '제출이 완료되었습니다.',
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

console.log(route.params);

onMounted(() => {
    console.log($q);
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
                > .text {
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
                > .text {
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

.is-form-dialog {
    .form-card {
        min-width: 300px;
    }
}
</style>
