import { format } from '@/lib/Format';

export function dateToDateStr(date: Date): string {
    return format(
        '%04d-%02d-%02d',
        date.getFullYear(),
        date.getMonth() + 1,
        date.getDate()
    );
}

export function dateToDatetimeStr(date: Date): string {
    return format(
        '%04d-%02d-%02d %02d:%02d:%02d',
        date.getFullYear(),
        date.getMonth() + 1,
        date.getDate(),
        date.getHours(),
        date.getMinutes(),
        date.getSeconds()
    );
}


export function dateToApiDateStr(year: number, month: number, day: number): string {
    return format(
        '%04d%02d%02d',
        year,
        month,
        day
    );
}
