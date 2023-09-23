export type WeatherInfo = {
    date: string;
    time: string;
    value: string;
};

export type BusInfo = {
    routeId: string;
    predictTime1: string;
    predictTime2?: string;
};
