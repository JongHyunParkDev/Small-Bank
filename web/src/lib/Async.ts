import { useErrorStore } from '@/stores/ErrorStore';

const errorStore = useErrorStore();

export async function process(firstProcess: () => void, lastProcess: () => void, func: () => void)
{
    firstProcess();
    try {
        return await func();
    }
    // TODO 일단 any 로 고정해두고 추 후 변경한다.
    // https://inpa.tistory.com/entry/TS-%F0%9F%93%98-%ED%83%80%EC%9E%85%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8-%EC%BB%A4%EC%8A%A4%ED%85%80-Error-%EC%B2%98%EB%A6%AC%ED%95%98%EA%B8%B0 로 변경 예정
    catch (error: any) {
        errorStore.addError(`[${error.code}] ${error.message}` );
        return Promise.reject(error);
    }
    finally {
        lastProcess();
    }
}

