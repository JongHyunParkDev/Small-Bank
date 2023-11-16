<template>
    <div class="survey-edit">
        <QTable
            class="sppd-table table"
            :columns="columns"
            :rows="rows"
            row-key="id"
            :virtual-scroll="true"
            :rows-per-page-options="[10000]"
            selection="single"
            v-model:selected="selectedRow"
            :separator="'cell'"
            :hide-selected-banner="true"
            :hide-pagination="true"
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
            class="survey-add-dialog"
            v-model="addDialogOption.visible"
            persistent
        >
            <QCard class="survey-card">
                <QForm @submit="addSurvey">
                    <QCardSection class="bg-primary">
                        <div class="text-h6 text-white">설문 항목 추가하기</div>
                    </QCardSection>
                    <QCardSection class="q-pa-md content">
                        <QInput
                            name="content"
                            outlined
                            stack-label
                            label="항목"
                            v-model="addDialogOption.content"
                            :rules="[(val) => val !== '' || '항목을 입력해주세요.']"
                        />
                        <QInput
                            name="category"
                            outlined
                            stack-label
                            label="카테고리"
                            v-model="addDialogOption.category"
                            :rules="[(val) => val !== '' || '카테고리을 입력해주세요.']"
                        />
                        <QBtnToggle
                            v-model="addDialogOption.isSort"
                            toggle-color="primary"
                            :options="[
                                {label: 'ASC (1,2,3,4)', value: true },
                                {label: 'DESC (4,3,2,1)', value: false },
                            ]"
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
        <QDialog
            class="survey-modify-dialog"
            v-model="modifyDialogOption.visible"
            persistent
        >
            <QCard class="survey-card">
                <QForm @submit="modifySurvey">
                    <QCardSection class="bg-primary">
                        <div class="text-h6 text-white">설문 항목 변경하기</div>
                    </QCardSection>
                    <QCardSection class="q-pa-md content">
                        <QInput
                            name="title"
                            outlined
                            stack-label
                            label="항목"
                            v-model="modifyDialogOption.content"
                            :rules="[(val) => val !== '' || '항목을 입력해주세요.']"
                        />
                        <QInput
                            name="category"
                            outlined
                            stack-label
                            label="카테고리"
                            v-model="modifyDialogOption.category"
                            :rules="[(val) => val !== '' || '카테고리을 입력해주세요.']"
                        />
                        <QBtnToggle
                            v-model="modifyDialogOption.isSort"
                            toggle-color="primary"
                            :options="[
                                {label: 'ASC (1,2,3,4)', value: true },
                                {label: 'DESC (4,3,2,1)', value: false },
                            ]"
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
import {defineProps, ref, Ref, inject} from 'vue';
import { useQuasar } from 'quasar';
import { Api } from '@/lib/Api';
import { PROCESS } from '@/lib/Async';
import { SurveyDetail } from '@/types/SurveyTypes';

const upProcessSpinner = inject<() => void>('upProcessSpinner');
const downProcessSpinner = inject<() => void>('downProcessSpinner');

const $q = useQuasar();

const props = defineProps({
    selectedSurveyIdx: {
        type: Number,
        required: true,
    },
});

const rows: Ref<Array<SurveyDetail>> = ref([]);

const selectedRow: Ref<Array<SurveyDetail>> = ref([]);

const columns = ref([
    {
        name: 'num',
        label: 'Num',
        field: 'num',
        required: true,
        align: 'left',
    },
    {
        name: 'content',
        label: 'Content',
        field: 'content',
        align: 'left',
    },
    {
        name: 'category',
        label: 'Category',
        field: 'category',
        align: 'left',
    },
    {
        name: 'isSort',
        label: 'Sort',
        field: 'isSort',
        align: 'left',
        format: (val) => (val ? 'ASC (1,2,3,4)' : 'DESC (4,3,2,1)'),
    },
]);

const addDialogOption = ref({
    visible: false,
    content: '',
    category: '',
    isSort: true,
});

const modifyDialogOption = ref({
    visible: false,
    id: -1,
    content: '',
    category: '',
    isSort: true,
});

function addSurvey() {
    if (upProcessSpinner && downProcessSpinner) {
        PROCESS(upProcessSpinner, downProcessSpinner, async () => {
            const result: SurveyDetail = await Api.post('user/surveyDetail', {
                surveyId: props.selectedSurveyIdx,
                content: addDialogOption.value.content,
                category: addDialogOption.value.category,
                isSort: addDialogOption.value.isSort
            });

            $q.notify({
                type: 'positive',
                message: '설문지 항목이 추가되었습니다.',
            });
            
            rows.value.push({
                id: result.id,
                num: rows.value.length + 1,
                content: result.content,
                category: result.category,
                isSort: result.isSort
            });

            addDialogOption.value.visible = false;
        });
    }
    // const result = Api.post();

    // rows.value.push({
    //     id: result.id ...
    // })
}

function modifySurvey() {
    // const result = Api.put();

    // selectedRow.value[0].content = result.content;
    // selectedRow.value[0].isSort = result.isSort;

    $q.notify({
        type: 'positive',
        message: '해당 항목이 변경되었습니다.',
    });

    modifyDialogOption.value.visible = false;
}

function showAddDialog() {
    addDialogOption.value.content = '';
    addDialogOption.value.category = '';
    addDialogOption.value.isSort = true;

    addDialogOption.value.visible = true;
}

function showModifyDialog() {
    modifyDialogOption.value.id = selectedRow.value[0].id;
    modifyDialogOption.value.content = selectedRow.value[0].content;
    modifyDialogOption.value.category = selectedRow.value[0].category;
    modifyDialogOption.value.isSort = selectedRow.value[0].isSort;

    modifyDialogOption.value.visible = true;
}

function deleteRow(evt: Event) {
    $q.notify({
        type: 'warning',
        position: 'center',
        message: '해당 설문 항목을 삭제할까요?',
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
        // await api.delete()
        console.log('삭제');
        // rows.value[0].isActive = true;
        // selectedRow 로는 설정되지 않음. 아마 deep copy 를 하는 듯함.
    }
}
</script>

<style lang="scss" scoped>
.survey-edit {
    width: 100%;
    height: 100%;

    > .table {
        height: calc(100% - 56px);
    }
}

.survey-add-dialog,
.survey-modify-dialog {
    .survey-card {
        min-width: 350px;
    }
}

</style>
