export class ApiError extends Error {
    code: string;

    constructor(messsage: string, code: string) {
        super(messsage);
        this.code = code;
    }
};

export interface BackEndError {
    code: string;
    message: string;
}

export const ApiCode = {
    // client
    UNKNOWN: '2000',
    REQUEST_FAILED: '2001',

    // server
    GENERIC: '1000',
    NOT_FOUND: '1001',

    INVALID_REQUEST: '1100',
    INVALID_ARGUMENT: '1101',
    MISSING_REQUIRED_FIELD: '1102',

    CALL_DATA_GO_API_ERROR: '1300',

    LOGIN_NEED: '1900',
    PERMISSION_DENIED: '1901',

    ID_PW_INVALID: '1990',
    FOUNT_NOT_ID: '1991',
    LOGIN_ERROR: '1999',
}

export const ApiMessage = {
    // client
    [ApiCode.UNKNOWN]: '에러가 발생하였습니다.',
    [ApiCode.REQUEST_FAILED]: '요청에 실패하였습니다.',

    // server
    [ApiCode.GENERIC]: '에러가 발생하였습니다.',
    [ApiCode.NOT_FOUND]: '내용(파일)을 찾을 수 없습니다.',

    [ApiCode.CALL_DATA_GO_API_ERROR]: '데이터를 불러오는데 에러가 발생했습니다.',

    [ApiCode.LOGIN_NEED]: '세션이 만료되었거나 로그인이 필요합니다.',
    [ApiCode.PERMISSION_DENIED]: '권한이 없습니다.',


    [ApiCode.ID_PW_INVALID]: 'PW 를 확인해주세요.',
    [ApiCode.FOUNT_NOT_ID]: 'ID 를 찾을 수 없습니다.',
    [ApiCode.LOGIN_ERROR]: '로그인 에러 발생하였습니다.',
}

