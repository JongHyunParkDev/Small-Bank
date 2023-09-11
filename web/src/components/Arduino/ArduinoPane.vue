<template>
    <div class="arduino">
        <div class="header">
            사용가능한
        </div>
        <div class="content">
            <QSplitter
                v-model="splitter"
            >
                <template v-slot:before>
                    <QTabs
                        v-model="tab"
                        vertical
                        class="tabs"
                    >
                        <QTab name="message" icon="chat" label="Message"></QTab>
                        <QTab name="weather" icon="sunny" label="Weather"></QTab>
                        <QTab name="bus" icon="directions_bus" label="Bus"></QTab>
                    </Qtabs>
                </template>

                <template v-slot:after>
                    <QTabPanels
                        v-model="tab"
                        animated
                        swipeable
                        vertical
                        transition-prev="jump-up"
                        transition-next="jump-up"
                    >
                        <QTabPanel name="message">
                            <div class="text-h4 q-mb-md">Mails</div>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                        </QTabPanel>

                        <QTabPanel name="weather">
                            <div class="text-h4 q-mb-md">Alarms</div>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                        </QTabPanel>

                        <QTabPanel name="bus">
                            <div class="text-h4 q-mb-md">Movies</div>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                        </QTabPanel>
                    </QTabPanels>
                </template>
            </QSplitter>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, Ref } from 'vue';

const splitter = 20;
const tab: Ref<string> = ref('mails');

</script>

<style lang="scss" scoped>
.arduino {
    position: absolute;
    top: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    padding: $spacing-sm;

    > .header {
        padding: $spacing-sm;
    }
    > .content {
        flex: 1;

        .tabs {
            color: $naver-dk;
        }
    }

}
</style>
