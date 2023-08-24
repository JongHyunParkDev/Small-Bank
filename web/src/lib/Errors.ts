export const code = {
    // client
    UNKNOWN: 2000,
    REQUEST_FAILED: 2001,
   

    // server
    NOT_FOUND: 1001,
}

export const message = {
    // client
    [code.UNKNOWN]: 'Error occurred.',    
    [code.REQUEST_FAILED]: 'Request failed.',

    // server
    [code.NOT_FOUND]: 'Request context not found.',

}

