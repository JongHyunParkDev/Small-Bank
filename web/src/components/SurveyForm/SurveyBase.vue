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
                    icon="mail"
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
            class="survey-add-dialog"
            v-model="addDialogOption.visible"
            persistent
        >
            <QCard class="survey-card">
                <QForm @submit="addSurvey">
                    <QCardSection class="bg-primary">
                        <div class="text-h6 text-white">설문조사 변경하기</div>
                    </QCardSection>
                    <QCardSection class="q-pa-md content">
                        <QInput
                            name="title"
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
            class="survey-modify-dialog"
            v-model="modifyDialogOption.visible"
            persistent
        >
            <QCard class="survey-card">
                <QForm @submit="modifySurvey">
                    <QCardSection class="bg-primary">
                        <div class="text-h6 text-white">설문조사 변경하기</div>
                    </QCardSection>
                    <QCardSection class="q-pa-md content">
                        <QInput
                            name="title"
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
import { ref, defineEmits } from 'vue';
import { useQuasar } from 'quasar';
import { dateToDateStr } from '@/lib/DateUtil';

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
    id: '',
    title: '',
    startDate: '',
    endDate: '',
    isActive: false,
});

const pagination = ref({
    rowsPerPage: 0,
});

const columns = ref([
    {
        name: 'id',
        label: 'Num',
        field: 'id',
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
        name: 'isActive',
        label: 'Active',
        field: 'isActive',
        required: true,
        align: 'left',
        format: (val) => (val ? '활성화' : '비활성화'),
    },
]);

// TODO num, id 정리가 필요함 num은 row 생성시 만들어주는 col 이고 id 는 db 에 있는 col 이여야 함.

const rows = ref([
    {
        id: 1,
        title: '이게나라냐',
        startDate: '2023-11-03',
        endDate: '2023-11-10',
        isActive: false,
    },
    {
        id: 2,
        title: '이게나라냐',
        startDate: '2023-11-05',
        endDate: '2023-11-10',
        isActive: false,
    },
    {
        id: 3,
        title: '이게나라냐',
        startDate: '2023-11-01',
        endDate: '2023-11-10',
        isActive: false,
    },
    {
        id: 4,
        title: '이게나라냐',
        startDate: '2023-11-02',
        endDate: '2023-11-10',
        isActive: false,
    },
    {
        id: 5,
        title: '이게나라냐',
        startDate: '2023-11-04',
        endDate: '2023-11-10',
        isActive: false,
    },
    {
        id: 6,
        title: '이게나라냐',
        startDate: '2023-11-01',
        endDate: '2023-11-10',
        isActive: false,
    },
    {
        id: 7,
        title: '이게나라냐',
        startDate: '2023-11-02',
        endDate: '2023-11-10',
        isActive: false,
    },
    {
        id: 8,
        title: '이게나라냐',
        startDate: '2023-11-04',
        endDate: '2023-11-10',
        isActive: false,
    },
]);
const selectedRow = ref([]);

function selectRow(selectOption) {
    // single
    emit('select-survey', selectOption.added ? selectOption.keys[0] : undefined);
}

function addSurvey() {
    console.log('a');
}

function modifySurvey() {
    console.log('a');
}

function toggleRow(evt: Event) {
    $q.notify({
        type: 'warning',
        position: 'center',
        message: `해당 설문지의 상태를 ${
            selectedRow.value.isActive ? '비활성화' : '활성화'
        }로 변경할까요?`,
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
        // await api.put()
        console.log('활성화비활성화');
        selectedRow.value[0].isActive = !selectedRow.value[0].isActive;
        // rows.value[0].isActive = true;
        // selectedRow 로는 설정되지 않음. 아마 deep copy 를 하는 듯함.
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
        // await api.delete()
        console.log('삭제');
        // rows.value[0].isActive = true;
        // selectedRow 로는 설정되지 않음. 아마 deep copy 를 하는 듯함.
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
    modifyDialogOption.value.startDate = selectedRow.value[0].startDate;
    modifyDialogOption.value.endDate = selectedRow.value[0].endDate;
    modifyDialogOption.value.title = selectedRow.value[0].title;

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

.survey-add-dialog,
.survey-modify-dialog {
    .survey-card {
        max-width: 350px;
        min-width: 350px;
    }
}
</style>
