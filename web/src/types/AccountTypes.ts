export type AccountBookDay = {
    id: string;
    num?: number;
};

export type DayOfTheWeek = {
    name: string;
    class?: string;
};

export type DayAccount = {
    id: string,
    date: string,
    time: string,
    category: string,
    memo: string,
    money: number,
    type: string,
}
