export type AccountBookDay = {
    id: string;
    num?: number;
};

export type DayOfTheWeek = {
    name: string;
    class?: string;
};

export type DayAccount = {
    id: string;
    date: string;
    time: string;
    category: string;
    memo: string;
    money: number;
    type: string;
};

export type AccountCategory = {
    id: string;
    category: string;
};

export type CategoryAccountMap = {
    [category: string]: CategoryAccountOjb;
};

export type CategoryAccountOjb = {
    list: Array<CategoryAccount>;
    income: string;
    spend: string;
};

export type CategoryAccount = {
    date: string;
    memo: string;
    type: string;
    money: string;
};

export type DateAccountMap = {
    [date: string]: DategoryAccountOjb;
};

export type DategoryAccountOjb = {
    list: Array<DateAccount>;
    income: string;
    spend: string;
};

export type DateAccount = {
    category: string;
    memo: string;
    type: string;
    money: string;
};
