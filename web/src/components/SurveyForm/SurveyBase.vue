<template>
    <div class="survey-base">
        <QTable
            class="sppd-table table"
            :columns="columns"
            :rows="rows"
            row-key="id"
            :pagination="pagination"
            :virtual-scroll="true"
            :rows-per-page-options="[0]"
            selection="single"
            v-model:selected="selectedRow"
            @selection="selectRow"
            :separator="'cell'"
        />
        <QPageSticky
            position="bottom-right"
            :offset="[18, 64]"
        >
            <QFab
                color="naver-bs"
                text-color="white"
                icon="keyboard_arrow_left"
                direction="left"
            >
                <QFabAction
                    external-label
                    label-position="top"
                    color="primary"
                    text-color="white"
                    icon="add"
                    label="추가"
                    @click="showAddDialog"
                />
                <QFabAction
                    external-label
                    label-position="top"
                    color="positive"
                    text-color="white"
                    icon="edit"
                    label="수정"
                    :disable="selectedRow.length === 0"
                    @click="showModifyDialog"
                />
                <QFabAction
                    external-label
                    label-position="top"
                    color="secondary"
                    text-color="white"
                    icon="check"
                    label="활성화"
                    :disable="selectedRow.length === 0"
                    @click="toggleRow"
                />
                <QFabAction
                    external-label
                    label-position="top"
                    color="negative"
                    text-color="white"
                    icon="close"
                    label="삭제"
                    :disable="selectedRow.length === 0"
                    @click="deleteRow"
                />
            </QFab>
        </QPageSticky>
        <QDialog
            class="form-dialog"
            v-model="addDialogOption.visible"
            persistent
        >
            <QCard class="form-card">
                <QForm @submit="addSurvey">
                    <QCardSection class="bg-primary">
                        <div class="text-h6 text-white">설문조사 추가하기</div>
                    </QCardSection>
                    <QCardSection class="q-pa-md content">
                        <QInput
                            name="survey-title"
                            outlined
                            stack-label
                            label="제목"
                            v-model="addDialogOption.title"
                            :rules="[(val) => val !== '' || '제목을 입력해주세요.']"
                        />
                        <QInput
                            class="q-mb-lg"
                            outlined
                            stack-label
                            label="시작일"
                            v-model="addDialogOption.startDate"
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
                                            v-model="addDialogOption.startDate"
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
                            outlined
                            stack-label
                            label="종료일"
                            v-model="addDialogOption.endDate"
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
                                            v-model="addDialogOption.endDate"
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
        <QDialog
            class="form-dialog"
            v-model="modifyDialogOption.visible"
            persistent
        >
            <QCard class="form-card">
                <QForm @submit="modifySurvey">
                    <QCardSection class="bg-primary">
                        <div class="text-h6 text-white">설문조사 변경하기</div>
                    </QCardSection>
                    <QCardSection class="q-pa-md content">
                        <QInput
                            name="survey-title"
                            outlined
                            stack-label
                            label="제목"
                            v-model="modifyDialogOption.title"
                            :rules="[(val) => val !== '' || '제목을 입력해주세요.']"
                        />
                        <QInput
                            class="q-mb-lg"
                            outlined
                            stack-label
                            label="시작일"
                            v-model="modifyDialogOption.startDate"
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
                                            v-model="modifyDialogOption.startDate"
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
                            outlined
                            stack-label
                            label="종료일"
                            v-model="modifyDialogOption.endDate"
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
                                            v-model="modifyDialogOption.endDate"
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
import { ref, Ref, defineEmits, inject } from 'vue';
import { PROCESS } from '@/lib/Async';
import { Api } from '@/lib/Api';
import { useQuasar, QTableColumn } from 'quasar';
import { dateToDateStr, datestrToApiDateStr } from '@/lib/DateUtil';
import { Survey } from '@/types/SurveyTypes';

const upProcessSpinner = inject<() => void>('upProcessSpinner');
const downProcessSpinner = inject<() => void>('downProcessSpinner');

const $q = useQuasar();

const emit = defineEmits(['select-survey']);

const addDialogOption = ref({
    visible: false,
    title: '',
    startDate: '',
    endDate: '',
    isActive: false,
});

const modifyDialogOption = ref({
    visible: false,
    id: -1,
    title: '',
    startDate: '',
    endDate: '',
    isActive: false,
});

const pagination = ref({
    rowsPerPage: 0,
});

const columns: Ref<Array<QTableColumn>> = ref([
    {
        name: 'num',
        label: 'Num',
        field: 'num',
        required: true,
        sortable: true,
        align: 'left',
    },
    {
        name: 'title',
        label: 'Title',
        field: 'title',
        align: 'left',
    },
    {
        name: 'startDate',
        label: 'StartDate',
        field: 'startDate',
        required: true,
        sortable: true,
        align: 'left',
    },
    {
        name: 'endDate',
        label: 'EndDate',
        field: 'endDate',
        required: true,
        sortable: true,
        align: 'left',
    },
    {
        name: 'active',
        label: 'Active',
        field: 'active',
        required: true,
        align: 'left',
        format: (val, row) => (val ? '활성화' : '비활성화'),
    },
]);
const rows: Ref<Array<Survey>> = ref([]);
// 반드시 [0] 만 존재
const selectedRow: Ref<Array<Survey>> = ref([]);

init();

function init() {
    if (upProcessSpinner && downProcessSpinner) {
        PROCESS(upProcessSpinner, downProcessSpinner, async () => {
            const result: Array<Survey> = await Api.get('user/surveys');
            result.forEach((el, idx) => {
                el.num = idx + 1;
                rows.value.push(el);
            });
        });
    }
}

function selectRow(selectOption) {
    // single
    emit(
        'select-survey',
        selectOption.added
            ? { id: selectOption.keys[0], active: selectOption.rows[0].active }
            : { id: undefined }
    );
}

function addSurvey() {
    if (upProcessSpinner && downProcessSpinner) {
        PROCESS(upProcessSpinner, downProcessSpinner, async () => {
            const result: Survey = await Api.post('user/survey', {
                title: addDialogOption.value.title,
                startDate: datestrToApiDateStr(addDialogOption.value.startDate),
                endDate: datestrToApiDateStr(addDialogOption.value.endDate),
            });

            $q.notify({
                type: 'positive',
                message: '설문지가 추가되었습니다.',
            });

            rows.value.push({
                id: result.id,
                num: rows.value.length + 1,
                title: result.title,
                startDate: result.startDate,
                endDate: result.endDate,
                active: result.active,
            });

            addDialogOption.value.visible = false;
        });
    }
}

function modifySurvey() {
    if (upProcessSpinner && downProcessSpinner) {
        PROCESS(upProcessSpinner, downProcessSpinner, async () => {
            const result = await Api.put('user/survey', {
                id: modifyDialogOption.value.id,
                title: modifyDialogOption.value.title,
                startDate: datestrToApiDateStr(modifyDialogOption.value.startDate),
                endDate: datestrToApiDateStr(modifyDialogOption.value.endDate),
            });

            $q.notify({
                type: 'positive',
                message: '설문지가 변경되었습니다.',
            });

            selectedRow.value[0].title = result.title;
            selectedRow.value[0].startDate = result.startDate;
            selectedRow.value[0].endDate = result.endDate;

            modifyDialogOption.value.visible = false;
        });
    }
}

function toggleRow(evt: Event) {
    $q.notify({
        type: 'warning',
        position: 'center',
        message: `해당 설문지의 상태를 ${
            selectedRow.value[0].active ? '비활성화' : '활성화'
        }로 변경할까요?`,
        caption: '비활성화시 이전 RESULT 내용은 삭제됩니다.',
        // html: true,
        timeout: 300000,
        actions: [
            {
                label: '예',
                color: 'white',
                handler: () => {
                    apply();
                },
            },
            {
                label: '아니요',
                color: 'white',
                handler: () => {
                    /* ... */
                },
            },
        ],
    });

    function apply() {
        if (upProcessSpinner && downProcessSpinner) {
            PROCESS(upProcessSpinner, downProcessSpinner, async () => {
                await Api.put('user/surveyIsActive', {
                    id: selectedRow.value[0].id,
                });

                selectedRow.value[0].active = !selectedRow.value[0].active;

                emit('select-survey', {
                    id: selectedRow.value[0].id,
                    active: selectedRow.value[0].active,
                });
            });
        }
    }
}

function deleteRow(evt: Event) {
    $q.notify({
        type: 'warning',
        position: 'center',
        message: '해당 설문지를 삭제할까요?',
        timeout: 300000,
        actions: [
            {
                label: '예',
                color: 'white',
                handler: () => {
                    apply();
                },
            },
            {
                label: '아니요',
                color: 'white',
                handler: () => {
                    /* ... */
                },
            },
        ],
    });

    async function apply() {
        if (upProcessSpinner && downProcessSpinner) {
            PROCESS(upProcessSpinner, downProcessSpinner, async () => {
                await Api.delete('user/survey', {
                    id: selectedRow.value[0].id,
                });

                if (selectedRow.value[0].num) rows.value.splice(selectedRow.value[0].num - 1, 1);
                rows.value.forEach((row, idx) => (row.num = idx + 1));

                emit('select-survey', {
                    id: undefined,
                    active: false,
                });
            });
        }
    }
}

function showAddDialog() {
    addDialogOption.value.title = '';
    addDialogOption.value.startDate = dateToDateStr(new Date());
    addDialogOption.value.endDate = dateToDateStr(new Date());

    addDialogOption.value.visible = true;
}

function showModifyDialog() {
    modifyDialogOption.value.id = selectedRow.value[0].id;
    modifyDialogOption.value.title = selectedRow.value[0].title;
    modifyDialogOption.value.startDate = selectedRow.value[0].startDate;
    modifyDialogOption.value.endDate = selectedRow.value[0].endDate;

    modifyDialogOption.value.visible = true;
}
</script>

<style lang="scss" scoped>
.survey-base {
    width: 100%;
    height: 100%;

    > .table {
        height: calc(100% - 56px);
    }
}

.form-dialog {
    .form-card {
        min-width: 350px;
    }
}
</style>
