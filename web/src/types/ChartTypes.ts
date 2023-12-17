export type ChartData = {
    name: string;
    y: number;
    drilldown: string;
};

export type IndexMap = {
    [category: string]: number;
};

export type DrilldownChartData = {
    id: string;
    name: string;
    data: Array<Array<string>>;
};
