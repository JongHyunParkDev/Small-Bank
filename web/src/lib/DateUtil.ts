import { format } from '@/lib/Format';

export function dateToDateStr(date: Date): string {
    return format(
        '%04d-%02d-%02d',
        date.getFullYear(),
        date.getMonth() + 1,
        date.getDate()
    );
}
