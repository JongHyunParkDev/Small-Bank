<template>
    <div class="survey-form">
        <QTabPanels
            class="tab-panels"
            v-model="selectedTab"
            animated
            transition-prev="jump-up"
            transition-next="jump-up"
        >
            <QTabPanel name="base">
                <SurveyBase
                    @select-survey="selectSurvey"
                />
            </QTabPanel>
            <QTabPanel name="edit">
                <SurveyEdit />
            </QTabPanel>
            <QTabPanel name="search">
                <SurveySearch />
            </QTabPanel>
        </QTabPanels>
        <QTabs
            class="tabs"
            indicator-color="transparent"
            outside-arrows
            dense
            v-model="selectedTab"
            @update:model-value="selectTab"
        >
            <QTab name="base" icon="description" label="Base"></QTab>
            <QTab name="edit" icon="edit" label="Edit" :disable="selectedSurveyIdx === undefined">
                <QTooltip
                    class="bg-naver-dk"
                    v-if="selectedSurveyIdx === undefined"
                >
                    BASE 의 설문조사 항목을 선택해주세요
                </QTooltip>
            </QTab>
            <QTab name="search" icon="search" label="Search" :disable="selectedSurveyIdx === undefined">
                <QTooltip
                    class="bg-naver-dk"
                    v-if="selectedSurveyIdx === undefined"
                >
                    BASE 의 설문조사 항목을 선택해주세요
                </QTooltip>
            </QTab>
        </Qtabs>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import SurveyBase from 'components/SurveyForm/SurveyBase.vue';
import SurveyEdit from 'components/SurveyForm/SurveyEdit.vue';
import SurveySearch from 'components/SurveyForm/SurveySearch.vue';

const selectedTab = ref('base');
const selectedSurveyIdx = ref(undefined);

function selectSurvey(idx) {
    selectedSurveyIdx.value = idx;
}

function selectTab(tab) {
    if (tab === 'base') selectedSurveyIdx.value = undefined;
}
</script>

<style lang="scss" scoped>
.survey-form {
    position: absolute;
    top: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;

    > .tab-panels {
        flex: 1;
    }

    > .tabs {
        background-color: $naver-bs;
        color: white;
    }
}
</style>
