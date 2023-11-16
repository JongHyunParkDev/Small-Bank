export type Survey = {
    id: number;
    num?: number;
    title: string;
    startDate: string;
    endDate: string;
    active: boolean;
};

export type SurveyDetail = {
    id: number;
    num?: number;
    content: string;
    category: string;
    isSort: boolean;
};
