import defaultAxios, { AxiosError, AxiosPromise, AxiosResponse } from 'axios';
import { ApiError } from '@/types/ErrorTypes';
import * as Errors from '@/lib/Errors';
import qs from 'qs';

const apiPrefix = '/api/';

const axios = defaultAxios.create({
    // 개발 목표
    timeout: 30000,
    withCredentials: false,
    // axios는 기본이 'bracket'인데, 그러면 https://...getList?names[]=a&names[]=b 처럼 request
    // url이 만들어지고, Spring Boot에서는 '['에 대해 다음과 같은 오류를 낸다.
    //   Invalid character found in the request target.
    //   The valid characters are defined in RFC 7230 and RFC 3986
    // repeat을 사용하면 Spring Boot에서 성공적으로 받는다.
    paramsSerializer: (params) => qs.stringify(params, { arrayFormat: 'repeat' }),
});

export const Api = {
    get: (url: string, params: object | undefined, config: any | undefined) => {
        params = params ? { params: params} : undefined;
        return process(axios.get(apiPrefix + url, appendAxiosConfig(params, config)));
    },

    post: (url: string, params: object | undefined, config: any | undefined) => {
        if (params === undefined)
            params = {};
        return process(axios.post(apiPrefix + url, appendAxiosConfig(params, config)));
    },

    put: (url: string, params: object | undefined, config: any | undefined) => {
        if (params === undefined)
            params = {};
        return process(axios.put(apiPrefix + url, appendAxiosConfig(params, config)));
    },

    delete: (url: string, params: object | undefined, config: any | undefined) => {
        params = params ? { data: params} : undefined;
        return process(axios.delete(apiPrefix + url, appendAxiosConfig(params, config)));
    },
};

function appendAxiosConfig(axiosConfig: any | undefined, config: any) {
    if (config === undefined)
        return axiosConfig;
    for (const key in config) if (Object.hasOwnProperty.call(config, key))
    {
        if (key === 'isBinary')
            add('responseType', 'arraybuffer');
        else if (key === 'isBlob')
            add('responseType', 'blob');
        else if ((key !== 'reqConvertSpec') && (key !== 'resConvertSpec'))
            add(key, config[key]);
    }
    return axiosConfig;

    function add(key: string, value: any)
    {
        if (axiosConfig === undefined)
            axiosConfig = {};
        axiosConfig[key] = value;
    }
}


function process(axiosPromise: AxiosPromise) {
    return axiosPromise.
    then((response: AxiosResponse) => {
        if (response === undefined)
            return undefined;
        return response.data;
    }, (error: AxiosError) => {
        console.log(error);

        if (error.response === undefined) {
            // ApiError 변경한다.
            // https://inpa.tistory.com/entry/TS-%F0%9F%93%98-%ED%83%80%EC%9E%85%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8-%EC%BB%A4%EC%8A%A4%ED%85%80-Error-%EC%B2%98%EB%A6%AC%ED%95%98%EA%B8%B0 로 변경 예정
            const returnError: ApiError = new Error(error.message);
            returnError.code = Errors.code.REQUEST_FAILED + '';
            return Promise.reject(returnError);
        }
        
        // 일단 any
        const data: any = error.response.data;

        let message = Errors.message[data.code];
        if (message){
            if (data.message)
                message += ' (' + data.message + ')';
        }
        else{
            message = Errors.message[Errors.code.UNKNOWN] + ' (' + data.code;
            if (data.message)
                message += ': ' + data.message;
            message += ')';
        }

        // ApiError 변경한다.
        // https://inpa.tistory.com/entry/TS-%F0%9F%93%98-%ED%83%80%EC%9E%85%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8-%EC%BB%A4%EC%8A%A4%ED%85%80-Error-%EC%B2%98%EB%A6%AC%ED%95%98%EA%B8%B0 로 변경 예정   
        const returnError: ApiError = new Error(message);
        returnError.code = data.code ? data.code : Errors.code.UNKNOWN; 

        return Promise.reject(returnError);
    })
}
