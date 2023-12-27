<template>
    <QBtnDropdown
        content-class="category-dropdown"
        color="primary" 
        :label="selectedCategory === undefined ? 'Select Category' : selectedCategory.text"
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
                        :disable="categoryText.length < 2"
                        @click.stop="saveAccountCategory"
                    />
                </div>
            </QItem>
            <QItem 
                dense
                clickable 
                v-close-popup 
                @click="selectCategory"
            >
                <QItemSection>
                    <QItemLabel class="flex justify-between">
                        <span class="category-label">
                            Photos
                        </span>
                        <QIcon 
                            class="category-icon" 
                            name="close"
                            @click.stop=""
                        />
                    </QItemLabel>
                </QItemSection>
            </QItem>
        </QList>
        <ProcessSpinner v-if="processCount > 0" />
    </QBtnDropdown>
</template>

<script setup lang="ts">
import { ref, Ref, computed, inject } from 'vue';
import ProcessSpinner from '@/components/ProcessSpinner.vue';
import { PROCESS } from '@/lib/Async';
import { Api } from '@/lib/Api';

const categoryList = ref([]);
const selectedCategory = ref();
const processCount: Ref<number> = ref(0);
const categoryText = ref('');

getAccountCategory();

function upProcessSpinner() {
    processCount.value = 1;
}
function downProcessSpinner() {
    processCount.value = 0;
}

function getAccountCategory() {
    PROCESS(upProcessSpinner, downProcessSpinner, async () => {
        const result: Array<any> = await Api.get('user/accountCategory');

        result.forEach(item => {
            categoryList.value.push({
                id: item.id,
                category: item.category
            })
        })

        console.log(result);
    });
}

function saveAccountCategory() {
    PROCESS(upProcessSpinner, downProcessSpinner, async () => {
        const result = await Api.post('user/accountCategory', {
            category: categoryText.value
        });

        console.log(result);
    });
}

function deleteAccountCategory() {
    PROCESS(upProcessSpinner, downProcessSpinner, async () => {
        const result = await Api.delete('user/accountCategory', {
            id: selectedCategory.value.id
        });

        console.log(result);
    });
}


function selectCategory() {
    console.log('나에~');
}   

</script>