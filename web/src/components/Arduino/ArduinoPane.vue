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
                    <span
                        class="refresh label"
                        @click="getSerialDto"
                    >
                        <QIcon name="refresh" size="1.5em"/>
                    </span>

                    <span class="date label">
                        업데이트 날짜: {{ displayUpdateDatetime }}
                    </span>
                </div>
            </fieldset>
        </div>
        <div class="content">
            <QSplitter
                class="splitter"
                v-model="splitter"
                :limits="[10, 30]"
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
                            <div class="content">
                                <div class="row">
                                    <QInput
                                        class="input"
                                        name="message"
                                        outlined
                                        stack-label
                                        label="Message"
                                        type="text"
                                        autogrow
                                        v-model="message"
                                        :rules="messageRule"
                                    />
                                    <QBtn
                                        class="btn"
                                        text-color="black"
                                        icon="send"
                                        :disable="! isMsgState"
                                        @click="sendMsg"
                                    />
                                </div>
                                <div class="row">
                                    <QExpansionItem
                                        class="expanstion-item"
                                        v-model="isMessageTip"
                                        icon="help"
                                        header-class="text-green"
                                        label="Account settings"
                                    >
                                        <QCard>
                                            <QCardSection>
                                                <p>- Mattrix 에 한 글자씩 1 초 씩 보여지며, 두 번 반복합니다.</p>
                                                <p>- 영어 대문자, 숫자, '/', ' ' 그리고 지정 특수 문자만 가능합니다.</p>
                                                <p>- '!' (고양이), '@' (하트), '#' (해골), '$' (돼지), '%' (해), '^' (비)</p>
                                            </QCardSection>
                                        </QCard>
                                    </QExpansionItem>
                                </div>
                            </div>

                        </QTabPanel>

                        <QTabPanel name="weather">
                            <div class="content">
                                <div
                                    v-for="(weather, idx) in weatherInfoList"
                                    :key="idx"
                                    class="row weather-row"
                                >
                                    <div class="icon label">
                                        <QIcon
                                            :class="+weather.value > 60 ? 'rainy' : 'sunny'"
                                            :name="+weather.value > 60 ? 'rainy' : 'sunny'" />
                                    </div>
                                    <div class="label">
                                        {{ weather.date }} {{ weather.time }}
                                    </div>
                                    <div class="label">
                                        {{ weather.value }}
                                    </div>
                                </div>
                            </div>
                        </QTabPanel>

                        <QTabPanel name="bus">
                            <div class="content">
                                <div
                                    v-for="(bus, idx) in busInfoList"
                                    :key="idx"
                                    class="row bus-row"
                                >
                                    <div class="icon label">
                                        <QIcon name="directions_bus" />
                                    </div>
                                    <div class="label">
                                        {{ bus.routeId }} 번
                                    </div>
                                    <div class="label">
                                        {{ bus.predictTime1 }} 분 {{ bus.predictTime2 }} {{ bus.predictTime2 ? '분' : '' }}
                                    </div>
                                </div>
                            </div>
                        </QTabPanel>
                    </QTabPanels>
                </template>
            </QSplitter>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useQuasar } from 'quasar'
import { ref, Ref, computed, inject } from 'vue';
import { process } from '@/lib/Async';
import { Api } from '@/lib/Api';
import { dateToDatetimeStr } from '@/lib/DateUtil';
import { BusInfo, WeatherInfo } from '@/types/ArduinoTypes';

const upProcessSpinner = inject<() => void>('upProcessSpinner');
const downProcessSpinner = inject<() => void>('downProcessSpinner');

let splitter = 30;
const $q = useQuasar();
if ($q.platform.is.desktop) splitter = 10;

const isState: Ref<boolean> = ref(false);

const updateDatetime: Ref<Date> = ref(new Date());
const tab: Ref<string> = ref('message');

const isMsgState: Ref<boolean> = ref(false);
const message: Ref<string> = ref('');
const isMessageTip: Ref<boolean> = ref(false);
const messageRegex = /^[A-Z0-9/ !@#$%^]+$/;
const messageRule = [
    (msg: string) => {
        return messageRegex.test(msg) || '영어 대문자, 숫자, 특수문자만 가능합니다.';
    }
];

const busInfoList: Ref<Array<BusInfo>> = ref([]);
const weatherInfoList: Ref<Array<WeatherInfo>> = ref([]);

const displayUpdateDatetime = computed(() => {
    return dateToDatetimeStr(updateDatetime.value);
})

function getSerialDto() {
    // 사실 ProcesSpinner 가 없는 경우는 없다. typescript 을 위해서...
    if (upProcessSpinner && downProcessSpinner) {
        process(upProcessSpinner, downProcessSpinner, async () => {
            const data = await Api.get('admin/serial/getSerialDto', undefined, undefined);

            updateDatetime.value = new Date();
            isState.value = data.state;
            isMsgState.value = data.msgState;

            busInfoList.value = data.busList;
            weatherInfoList.value = data.weatherList;
        });
    }
}

function sendMsg() {
    // 사실 ProcesSpinner 가 없는 경우는 없다. typescript 을 위해서...
    if (upProcessSpinner && downProcessSpinner) {
        process(upProcessSpinner, downProcessSpinner, async () => {
            const data = await Api.post('admin/serial/send', {
                msg: message.value
            }, undefined);

            updateDatetime.value = new Date();
            isState.value = data.state;
            isMsgState.value = data.msgState;

            getSerialDto();
        });
    }
}

getSerialDto();

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
                width: 100%;

                .content {
                    display: flex;
                    flex-direction: column;

                    > .row {
                        display: flex;

                        > .input {
                            flex: 1;
                        }

                        > .btn {
                            margin: $spacing-tn 0px;
                            margin-left: $spacing-sm;
                            height: 49px;
                        }
                        > .expanstion-item {
                            width: 100%;
                        }
                    }

                    > .bus-row {
                        border-bottom: 1px solid $grey-6;
                        margin-bottom: $spacing-md;

                        > .label {
                            flex: 1;
                            font-size: 1.2em;
                            text-align: center;
                        }

                        > .icon {
                            color: $naver-bs;
                            font-size: 1.5em;
                        }
                    }

                    > .weather-row {
                        border-bottom: 1px solid $grey-6;
                        margin-bottom: $spacing-sm;

                        > .label {
                            flex: 1;
                            font-size: 1em;
                            text-align: center;
                            white-space: pre;
                        }

                        > .icon {
                            color: $naver-bs;
                            font-size: 1.2em;

                            .rainy {
                                color: $blue-grey-9;
                            }

                            .sunny {
                                color: $orange-9;
                            }
                        }
                    }
                }

                .info {

                }
            }

            .tabs {
                color: $naver-dk;
            }
        }
    }
}
</style>
