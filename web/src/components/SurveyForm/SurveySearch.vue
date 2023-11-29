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
                    @click="search(false)"
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
            :virtual-scroll="true"
            selection="single"
            :separator="'cell'"
            :hide-selected-banner="true"
            :hide-pagination="true"
            v-model:selected="selectedRow"
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
            @update:model-value="search($event, false)"
        />
    </div>
</template>

<script setup lang="ts">
import { ref, defineProps, inject } from 'vue';
import * as Export from '@/lib/Export';
import { Api } from '@/lib/Api';
import { PROCESS } from '@/lib/Async';

const upProcessSpinner = inject<() => void>('upProcessSpinner');
const downProcessSpinner = inject<() => void>('downProcessSpinner');

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

const pageObject = ref({
    page: 1,
    max: 0,
    pagePerSize: 100,
});

const columns = ref([
    {
        name: 'num',
        label: 'Num',
        field: 'num',
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
]);

const rows: any = ref([]);

const selectedRow = ref([]);

function exportRow() {
//     was 에서 떨궈주자
    Export.toCsv(rows.value, 'export');
}

function search(num: number, isInit: boolean) {
    if (upProcessSpinner && downProcessSpinner) {
        PROCESS(upProcessSpinner, downProcessSpinner, async () => {
            const result = await Api.get('user/surveyUsers', {
                surveyId: props.selectedSurveyIdx,
                name: searchParams.value.name,
                dept: searchParams.value.dept,
                gender: searchParams.value.gender,
                size: pageObject.value.pagePerSize,
                page: pageObject.value.page - 1
            });

            rows.value = [];

            result.content.forEach((userDto: any, idx) => {
                userDto.num = idx + 1;
                userDto.sum = 0;

                if (isInit && idx === 0) {
                    userDto.surveyUserResultDtoList.forEach(resultDto => {
                        columns.value.push({
                            name: resultDto.category,
                            label: resultDto.category,
                            field: resultDto.category,
                            align: 'left'
                        })
                    })
                }

                userDto.surveyUserResultDtoList.forEach(resultDto => {
                    userDto[resultDto.category] = resultDto.score;
                    userDto.sum += resultDto.score;
                })

                rows.value.push(userDto);
            });

            pageObject.value.max = result.totalPages;
        });
    }
}

search(1, true);

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
