<template>
    <QBtnDropdown
        color="primary"
        no-caps
        menu-anchor="bottom left"
        menu-self="top start"
        :label="selectedCategory === undefined ? 'Select Category' : selectedCategory"
    >
        <QList>
            <QItem>
                <div class="flex">
                    <QInput
                        class="q-mr-md"
                        style="width: 100px"
                        v-model="categoryText"
                        dense
                        outlined
                        stack-label
                    />
                    <QBtn
                        dense
                        class="q-px-md"
                        label="추가"
                        color="primary"
                        :disable="!enableAddCategory"
                        @click.stop="saveAccountCategory"
                    />
                </div>
            </QItem>
            <div style="max-height: 200px; overflow-y: auto">
                <QItem
                    v-for="(item, idx) in categoryList"
                    :key="idx"
                    dense
                    clickable
                    v-close-popup
                    @click="selectCategory(item)"
                >
                    <QItemSection>
                        <QItemLabel class="flex justify-between">
                            <span class="q-my-sm">
                                {{ item.category }}
                            </span>
                            <QBtn
                                dense
                                flat
                                icon="close"
                                @click.stop="deleteAccountCategory(item, idx)"
                            />
                        </QItemLabel>
                    </QItemSection>
                </QItem>
            </div>
        </QList>
        <ProcessSpinner v-if="processCount > 0" />
    </QBtnDropdown>
</template>

<script setup lang="ts">
import { ref, Ref, computed, defineEmits, defineProps } from 'vue';
import ProcessSpinner from '@/components/ProcessSpinner.vue';
import { PROCESS } from '@/lib/Async';
import { Api } from '@/lib/Api';
import { AccountCategory } from '@/types/AccountTypes';

const props = defineProps({
    propCategory: {
        type: String,
        required: false,
    },
});

const emit = defineEmits(['select-category']);

const enableAddCategory = computed(() => {
    return (
        categoryText.value.length > 0 &&
        categoryList.value.every((item) => item.category !== categoryText.value)
    );
});

const categoryList: Ref<Array<AccountCategory>> = ref([]);
const selectedCategory = ref();
const processCount: Ref<number> = ref(0);
const categoryText = ref('');

init();

function upProcessSpinner() {
    processCount.value = 1;
}
function downProcessSpinner() {
    processCount.value = 0;
}

function getAccountCategory() {
    PROCESS(upProcessSpinner, downProcessSpinner, async () => {
        const result: Array<AccountCategory> = await Api.get('user/accountCategory');

        result.forEach((item) => {
            categoryList.value.push({
                id: item.id,
                category: item.category,
            });
        });
    });
}

function saveAccountCategory() {
    PROCESS(upProcessSpinner, downProcessSpinner, async () => {
        const result: AccountCategory = await Api.post('user/accountCategory', {
            category: categoryText.value,
        });

        categoryList.value.push({
            id: result.id,
            category: result.category,
        });

        categoryText.value = '';
    });
}

function deleteAccountCategory(item, idx) {
    PROCESS(upProcessSpinner, downProcessSpinner, async () => {
        await Api.delete('user/accountCategory', {
            id: item.id,
        });

        categoryList.value.splice(idx, 1);
    });
}

function selectCategory(item) {
    selectedCategory.value = item.category;
    emit('select-category', item.category);
}

function init() {
    if (props.propCategory) {
        selectedCategory.value = props.propCategory;
    }
    getAccountCategory();
}
</script>
