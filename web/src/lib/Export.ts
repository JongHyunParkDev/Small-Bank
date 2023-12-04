/** @module */
import FileSaver from 'file-saver';
import Papa from 'papaparse';

export function toCsv(data: Array<any>, fileName: string) {
    const outputFileName = `${fileName}.csv`;
    const blob = toCsvBlob(data);

    FileSaver.saveAs(blob, outputFileName);
    return outputFileName;
}

export function toCsvBlob(data: Array<any>) {
    const ret = '\uFEFF' + Papa.unparse(data);
    return new Blob([ret], {type: 'text/csv;charset=utf-8'});
}
