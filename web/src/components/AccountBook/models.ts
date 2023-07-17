export type Account = {
    spend: number;
    income: number;
};

export type AccountBookDay = {
    id: string;
    num?: number;
    account?: Account;
};

export type DayOfTheWeek = {
    name: string;
    class?: string;
};