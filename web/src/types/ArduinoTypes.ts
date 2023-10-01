export type SerialInfo = {
    busMap: BusMap;
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

export type BusMap = {
    [stationName: string] : Array<BusInfo>;
}

export type BusInfo = {
    routeId: string;
    predictTime1: string;
    predictTime2?: string;
};
