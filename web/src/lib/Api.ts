import defaultAxios, { AxiosError, AxiosPromise, AxiosResponse } from 'axios';
import qs from 'qs';

const apiPrefix = '/api/';

const axios = defaultAxios.create({
    // 개발 목표
    // 최대 30초 이상 기간이 걸리지 않도록 하자.
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
        params = params ? { params: params} : undefined;
        return process(axios.put(apiPrefix + url, appendAxiosConfig(params, config)));
    },

    delete: (url: string, params: object | undefined, config: any | undefined) => {
        params = params ? { params: params} : undefined;
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
        // TODO Backend 단에서 code 와 message 를 던진 후 만든다.
        console.log(error);
        throw error;
    })
}
