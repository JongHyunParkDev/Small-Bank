export type ChartData = {
    name: string,
    y: number,
    drilldown: string
}

export type DrilldownChartData = {
    name: string,
    id: string,
    data: Array<Array<any>>
}

export type IndexMap = {
    [category: string]: number
}