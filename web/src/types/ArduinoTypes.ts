export type SerialInfo = {
    busList: Array<BusInfo>;
    weatherList: Array<WeatherInfo>;
    msgState: boolean;
    state: boolean;
}

export type WeatherInfo = {
    date: string;
    sky: number;
    rain: number;
    snow: number;
    tmpMax: number;
    tmpMin: number;
    iconSrc?: string;
};

export type BusInfo = {
    stationName: string,
    routeId: string;
    predictTime1: string;
    predictTime2?: string;
};
