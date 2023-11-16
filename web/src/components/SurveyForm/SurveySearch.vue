<template>
    <div class="survey-search">
        <div class="header">
            <QInput
                dense
                class="q-mr-sm input"
                outlined
                stack-label
                label="이름"
                v-model="searchParams.name"
            />
            <QInput
                dense
                class="q-mr-sm input"
                outlined
                stack-label
                label="부서"
                v-model="searchParams.dept"
            />
            <QBtnToggle
                v-model="searchParams.gender"
                toggle-color="primary"
                :options="[
                    {label: 'ALL', value: undefined },
                    {label: '남자', value: true },
                    {label: '여자', value: false }
                ]"
            />
            <div class="btn-area">
                <QBtn
                    class="btn q-mr-sm"
                    color="primary"
                    label="검색"
                    @click="search"
                />
                <QBtn
                    color="white"
                    text-color="black"
                    label="export"
                    @click="exportRow"
                />
            </div>
        </div>
        <QTable
            class="sppd-table table"
            :columns="columns"
            :rows="rows"
            row-key="id"
            :pagination="pagination"
            :virtual-scroll="true"
            :rows-per-page-options="[200]"
            selection="single"
            v-model:selected="selectedRow"
            :separator="'cell'"
            :hide-selected-banner="true"
            :hide-pagination="true"
        />
        <QPagination
            class="paging flex flex-center"
            color="green-7"
            v-model="pageObject.page"
            :max="pageObject.max"
            max-pages="5"
            :ellipses="false"
            :boundary-numbers="false"
            direction-links
            boundary-links
        />
    </div>
</template>

<script setup lang="ts">
import { ref, defineProps } from 'vue';
import * as Export from '@/lib/Export';

const props = defineProps({
    selectedSurveyIdx: {
        type: Number,
        required: true,
    },
});

const searchParams = ref({
    name: '',
    dept: '',
    gender: undefined
})

function exportRow() {
//     was 에서 떨궈주자
    Export.toCsv(rows.value, 'export');
}

function search() {
//
}

const pagination = ref({
    // sortBy: 'desc',
    // descending: false,
    page: 1,
    rowsPerPage: 200
});

const columns = ref([
    {
        name: 'id',
        label: 'Num',
        field: 'id',
        required: true,
        align: 'left',
    },
    {
        name: 'name',
        label: 'Name',
        field: 'name',
        align: 'left',
    },
    {
        name: 'birthDay',
        label: 'BirthDay',
        field: 'birthDay',
        required: true,
        align: 'left',
    },
    {
        name: 'gender',
        label: 'Gender',
        field: 'gender',
        required: true,
        align: 'left',
        format: (val) => (val ? '남' : '여'),
    },
    {
        name: 'dept',
        label: 'Dept',
        field: 'dept',
        required: true,
        align: 'left',
    },
    {
        name:'sum',
        label: '총합',
        field: 'sum',
        align: 'left'
    },
    {
        name:'물리환경',
        label: '물리환경',
        field: '물리환경',
        align: 'left'
    },
    {
        name:'직무요구',
        label: '직무요구',
        field: '직무요구',
        align: 'left'
    },
    {
        name:'직무자율',
        label: '직무자율',
        field: '직무자율',
        align: 'left'
    },
    {
        name:'관계갈등',
        label: '관계갈등',
        field: '관계갈등',
        align: 'left'
    },
    {
        name:'직무불안정',
        label: '직무불안정',
        field: '직무불안정',
        align: 'left'
    },
    {
        name:'조직체계',
        label: '조직체계',
        field: '조직체계',
        align: 'left'
    },
    {
        name:'보상부적절',
        label: '보상부적절',
        field: '보상부적절',
        align: 'left'
    },
    {
        name:'직장문화',
        label: '직장문화',
        field: '직장문화',
        align: 'left'
    },

]);



// TODO num, id 정리가 필요함 num은 row 생성시 만들어주는 col 이고 id 는 db 에 있는 col 이여야 함.

const rows = ref([
    {
        id: 1,
        num: 3, // 위 내용
        name: '대상자',
        birthDay: '1995-07-07',
        gender: false,
        dept: '여기야',
        sum: 99,
        물리환경: 11,
        직무요구: 11,
        직무자율: 11,
        관계갈등: 11,
        직무불안정: 11,
        조직체계: 11,
        보상부적절: 11,
        직장문화: 11
    },
    {
        id: 2   ,
        num: 3, // 위 내용
        name: '대상자',
        birthDay: '1995-07-07',
        gender: false,
        dept: '여기야',
        sum: 99,
        물리환경: 11,
        직무요구: 11,
        직무자율: 11,
        관계갈등: 11,
        직무불안정: 11,
        조직체계: 11,
        보상부적절: 11,
        직장문화: 11
    },
]);


const pageObject = ref({
    page: 1,
    max: 0
});

const selectedRow = ref([]);

</script>

<style lang="scss" scoped>
.survey-search {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;

    > .header {
        display: flex;
        margin: $spacing-sm;
        flex-wrap: wrap;
        align-items: center;

        > .input {
            max-width: 45%;
        }

        > .btn-area {
            display: flex;
            padding: $spacing-tn 0px;
            margin-left: auto;
        }
    }

    > .table {
        flex: 1;
    }

    > .paging {
        margin: $spacing-md 0px;
    }
}
</style>
