<template>
    <div>
        <QItem
            clickable
            @click="toggleDarkmode"
        >
            <QItemSection
                avatar
            >
                <QIcon name="contrast"/>
            </QItemSection>
            <QItemSection>
                <QItemLabel>다크모드</QItemLabel>
            </QItemSection>
        </QItem>
        <QItem
            clickable
            @click="toggleFullScreen"
        >
            <QItemSection
                avatar
            >
                <QIcon :name="fullScreenIcon"/>
            </QItemSection>
            <QItemSection>
                <QItemLabel>Screen</QItemLabel>
            </QItemSection>
        </QItem>
        <QItem
            clickable
            @click="showVersion"
        >
            <QItemSection
                avatar
            >
                <QIcon name="info"/>
            </QItemSection>
            <QItemSection>
                <QItemLabel>Version</QItemLabel>
            </QItemSection>
        </QItem>
    </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useQuasar } from 'quasar';
const localStorageKey = 'sb-option';

const $q = useQuasar();

function toggleDarkmode() {
    $q.dark.toggle();
    setLocalStorage();
}

function toggleFullScreen() {
    $q.fullscreen.toggle();
}

const fullScreenIcon = computed(() => {
    return $q.fullscreen.isActive ? 'fullscreen_exit' : 'fullscreen';
});

function showVersion() {
    const version = process.env.VERSION;
    const updateDate = process.env.UPDATE_DATE;

    $q.dialog({
        title: 'Small Bank Info',
        message: `Version: ${version} <br> UpdateDate: ${updateDate}`,
        html: true,
    })
}
function setLocalStorage() {
    const value = {
        dark: $q.dark.isActive
    }
    localStorage.setItem(localStorageKey, JSON.stringify(value));
}


function getLocalStorage() {
    const opetionStr = localStorage.getItem(localStorageKey);
    if (opetionStr) {
        const value = JSON.parse(opetionStr);
        if (value.dark) $q.dark.set(true)
    }
}

onMounted(() => {
    getLocalStorage();
});
</script>
