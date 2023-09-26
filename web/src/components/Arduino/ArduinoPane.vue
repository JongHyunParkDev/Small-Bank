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
                                        :disable="! isMsgState || ! isState"
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
                            <div class="weather-content">
                                <div
                                    v-for="(weather, idx) in weatherInfoList"
                                    :key="idx"
                                    class="weather-row"
                                >
                                    <div class="header">
                                        {{ getApiDateToDateStr(weather.date) }}
                                    </div>
                                    <img
                                        class="img"
                                        :src="weather.iconSrc"
                                    >
                                    <div class="label">
                                        최고 {{ weather.tmpMax }} °C
                                    </div>
                                    <div class="label">
                                        최저 {{ weather.tmpMin }} °C
                                    </div>
                                </div>
                            </div>
                        </QTabPanel>

                        <QTabPanel name="bus">
                            <div class="content">
                                <QExpansionItem
                                    expand-separator
                                    v-for="(busInfoList, key) in busMap"
                                    :key="key"
                                    header-class="bus-map"
                                    default-opened
                                    :label="key + ''"
                                >
                                    <div
                                        v-for="(bus, idx2) in busInfoList"
                                        :key="idx2"
                                        class="row bus-row"
                                    >
                                        <div class="icon label">
                                                <QIcon name="directions_bus" />
                                            </div>
                                            <div class="label">
                                                {{ bus.routeId }} 번
                                            </div>
                                            <div class="label">
                                                {{ bus.predictTime1 }}분{{ bus.predictTime2 ? '/' : '' }}{{ bus.predictTime2 }}{{ bus.predictTime2 ? '분' : '' }}
                                            </div>
                                        <div class="row bus-row" v-if="busInfoList.length === 0">
                                            <div class="label">
                                                운영중인 버스가 없습니다.
                                            </div>
                                        </div>
                                    </div>
                                </QExpansionItem>
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
import { dateToDatetimeStr, apiDateToDateStr } from '@/lib/DateUtil';
import { SerialInfo, BusMap, WeatherInfo } from '@/types/ArduinoTypes';
import { compare } from '@/lib/StrUtil';

import ClearDayIcon from '@/assets/icons/clear-day.svg';
import CloudyIcon from '@/assets/icons/cloudy.svg';
import PcdIcon from '@/assets/icons/partly-cloudy-day.svg';
import PcdrIcon from '@/assets/icons/partly-cloudy-day-rain.svg';
import PcdsIcon from '@/assets/icons/partly-cloudy-day-snow.svg';
import RainIcon from '@/assets/icons/rain.svg';
import SnowIcon from '@/assets/icons/snow.svg';

const upProcessSpinner = inject<() => void>('upProcessSpinner');
const downProcessSpinner = inject<() => void>('downProcessSpinner');

let splitter = 30;
const $q = useQuasar();
if ($q.platform.is.desktop) {
    splitter = 10;
}

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

const busMap: Ref<BusMap> = ref({});
const weatherInfoList: Ref<Array<WeatherInfo>> = ref([]);

const displayUpdateDatetime = computed(() => {
    return dateToDatetimeStr(updateDatetime.value);
})

function getApiDateToDateStr(date: string) {
    return apiDateToDateStr(date);
}

function getSerialDto() {
    // 사실 ProcesSpinner 가 없는 경우는 없다. typescript 을 위해서...
    if (upProcessSpinner && downProcessSpinner) {
        process(upProcessSpinner, downProcessSpinner, async () => {
            const data: SerialInfo = await Api.get('admin/serial/getSerialDto');

            updateDatetime.value = new Date();
            isState.value = data.state;
            isMsgState.value = data.msgState;

            for (const key in data.busMap) {
                data.busMap[key].sort((a, b) => compare(a.routeId, b.routeId));
            }

            busMap.value = data.busMap;

            data.weatherList.forEach(weather => {
                let target = ClearDayIcon;

                if (weather.sky < 1.5) target = ClearDayIcon;
                else if (weather.sky < 3) target = PcdIcon;
                else target = CloudyIcon;

                if (weather.snow > 0 && weather.rain == 0) {
                    if (weather.sky >= 3) target = SnowIcon;
                    else target = PcdsIcon;
                }

                if (weather.rain > 0) {
                    if (weather.sky >= 3) target = RainIcon;
                    else target = PcdrIcon;
                }

                weather.iconSrc = target;
            });
            data.weatherList.sort((a, b) => compare(a.date, b.date));
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
            });

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

                    ::v-deep .bus-map {
                        display: flex;
                        flex-direction: column;
                        background-color: $naver-dk;
                        color: white;
                        border: 2px solid $grey-6;
                        border-radius: $spacing-lg;
                        box-shadow: 3px 3px 3px $grey-5;
                    }

                    ::v-deep .bus-row {
                        border-bottom: 1px solid $grey-6;
                        margin: $spacing-sm 0px;

                        > .label {
                            flex: 1;
                            font-size: 1em;
                            line-height: 24px;
                            text-align: center;
                        }

                        > .icon {
                            color: $naver-bs;
                            font-size: 2em;
                        }
                    }
                }

                .weather-content {
                    display: grid;
                    height: 100%;
                    width: 100%;
                    grid-template-columns: 1fr 1fr;
                    gap: $spacing-sm;
                    > .weather-row {
                        display: flex;
                        flex-direction: column;
                        border: 2px solid $grey-6;
                        border-radius: $spacing-lg;
                        box-shadow: 3px 3px 3px $grey-5;

                        > .header {
                            width: 100%;
                            background-color: $naver-bs;
                            color: white;
                            padding: $spacing-sm;
                            text-align: center;
                            font-weight: bold;
                            border-radius: $spacing-ml $spacing-ml 0px 0px;
                            border-bottom: 2px solid $grey-6;
                        }

                        > .img {
                            flex: 1;
                            margin: 0 auto;
                            width: 100%;
                        }

                        > .label {
                            width: 100%;
                            text-align: center;
                            margin-bottom: $spacing-sm;
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
