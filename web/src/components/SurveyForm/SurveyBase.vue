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
        />
        <QPageSticky position="bottom-right" :offset="[18, 64]">
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
                />
                <QFabAction
                    external-label
                    label-position="top"
                    color="positive"
                    text-color="white"
                    icon="edit"
                    label="수정"
                    :disable="selectedRow.length === 0"
                />
                <QFabAction
                    external-label
                    label-position="top"
                    color="secondary"
                    text-color="white"
                    icon="mail"
                    label="활성화"
                    :disable="selectedRow.length === 0"
                />
                <QFabAction
                    external-label
                    label-position="top"
                    color="negative"
                    text-color="white"
                    icon="close"
                    label="삭제"
                    :disable="selectedRow.length === 0"
                />
            </QFab>
        </QPageSticky>
    </div>
</template>

<script setup lang="ts">
import { ref, defineEmits } from 'vue';

const emit = defineEmits(['select-survey']);

const pagination = ref({
    rowsPerPage: 0
})

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
        format: (val) => val ? '활성화' : '비활성화',
    },
]);
const rows = ref([
    {
        id: 1,
        title: '이게나라냐',
        startDate: '2023-11-03',
        endDate: '2023-11-10'
    },
    {
        id: 2,
        title: '이게나라냐',
        startDate: '2023-11-05',
        endDate: '2023-11-10'
    },
    {
        id: 3,
        title: '이게나라냐',
        startDate: '2023-11-01',
        endDate: '2023-11-10'
    },
    {
        id: 4,
        title: '이게나라냐',
        startDate: '2023-11-02',
        endDate: '2023-11-10'
    },
    {
        id: 5,
        title: '이게나라냐',
        startDate: '2023-11-04',
        endDate: '2023-11-10'
    },
    {
        id: 6,
        title: '이게나라냐',
        startDate: '2023-11-01',
        endDate: '2023-11-10'
    },
    {
        id: 7,
        title: '이게나라냐',
        startDate: '2023-11-02',
        endDate: '2023-11-10'
    },
    {
        id: 8,
        title: '이게나라냐',
        startDate: '2023-11-04',
        endDate: '2023-11-10'
    },
]);
const selectedRow = ref([]);

function selectRow(selectOption) {
    // single
    emit('select-survey', selectOption.keys[0]);
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
</style>
