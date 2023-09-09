import defaultAxios, { AxiosError, AxiosPromise, AxiosResponse } from 'axios';
import { ApiError, ApiCode, ApiMessage, BackEndError } from '@/lib/Errors';
import { isObject } from 'lodash';
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
        console.error(error);

        // client 발생 시점
        if (error.response === undefined) {
            const returnError = new ApiError(error.message, ApiCode.REQUEST_FAILED.toString());
            return Promise.reject(returnError);
        }
        
        // server 발생 시점
        if (isObject(error.response.data)) {
            const data = error.response.data as BackEndError; 
        
            let message = ApiMessage[data.code];
            if (message){
                if (data.message)
                    message += ' (' + data.message + ')';
            }
            else{
                message = ApiMessage[ApiCode.UNKNOWN] + ' (' + data.code;
                if (data.message)
                    message += ': ' + data.message;
                message += ')';
            }
    
            const returnError = new ApiError(message, data.code ? data.code : ApiCode.UNKNOWN.toString());
    
            return Promise.reject(returnError);
        }
        else {
            Promise.reject(new Error(error.message));
        }
    })
}
