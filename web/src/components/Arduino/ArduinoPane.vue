<template>
    <div class="arduino">
        <div class="header">
            <fieldset class="state-pane">
                <legend>Toy 상태</legend>
                <div class="row">
                    <span class="label">
                        사용 가능 상태
                    </span>
                    <span class="state">
                        <QToggle
                            size="sm"
                            disable
                            v-model="isState"
                            color="green"
                        />
                    </span>
                    <span class="refresh label">
                        <QIcon name="refresh" size="1.5em"/>
                    </span>

                    <span class="date label">
                        업데이트 날짜:
                    </span>
                </div>
            </fieldset>
        </div>
        <div class="content">
            <QSplitter
                class="splitter"
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
                        class="tab-panels"
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
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quis praesentium cumque magnam odio iure quidem, quod illum numquam possimus obcaecati commodi minima assumenda consectetur culpa fuga nulla ullam. In, libero.</p>
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
import { useQuasar } from 'quasar'
import { ref, Ref } from 'vue';

let splitter = 30;
const $q = useQuasar();
if ($q.platform.is.desktop) splitter = 10;

const tab: Ref<string> = ref('message');
const isState: Ref<boolean> = ref(false);

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
        width: auto;
        font-size: 1.2em;

        > .state-pane {
            border-color: $naver-bs;

            > .row {
                > .refresh {
                    color: $naver-dk;
                    margin-left: auto;

                    &:hover {
                        cursor: pointer;
                        color: $green-10;
                    }
                }

                > .date {
                    margin-left: $spacing-sm;
                }

                > .label {
                    padding: $spacing-sm;
                }

            }
        }
    }
    > .content {
        flex: 1;
        margin: 0px $spacing-sm;
        border: 2px solid $naver-dk;

        > .splitter {
            height: 100%;

            .tab-panels {
                position: absolute;
                height: 100%;
            }

            .tabs {
                color: $naver-dk;
            }
        }
    }
}
</style>
