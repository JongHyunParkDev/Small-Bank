import defaultAxios from 'axios';
import qs from 'qs';

const apiPrefix = '/api/';

const axios = defaultAxios.create({
    // axios는 기본이 'bracket'인데, 그러면 https://...getList?names[]=a&names[]=b 처럼 request
    // url이 만들어지고, Spring Boot에서는 '['에 대해 다음과 같은 오류를 낸다.
    //   Invalid character found in the request target.
    //   The valid characters are defined in RFC 7230 and RFC 3986
    // repeat을 사용하면 Spring Boot에서 성공적으로 받는다.
    paramsSerializer: (params) => qs.stringify(params, { arrayFormat: 'repeat' }),
});

export const Api = {
    get: (url: string, data: object) => {
        return axios.get(apiPrefix + url, data);
    },

    post: (url: string, data: object) => {
        return axios.post(apiPrefix + url, data);
    },

    put: (url: string, data: object) => {
        return axios.put(apiPrefix + url, data);
    },

    delete: (url: string, data: object) => {
        return axios.delete(apiPrefix + url, data);
    },
};
