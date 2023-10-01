import { useErrorStore } from '@/stores/ErrorStore';
import { ApiError } from '@/lib/Errors';

const errorStore = useErrorStore();

export async function process(firstProcess: () => void, lastProcess: () => void, func: () => void)
{
    firstProcess();
    try {
        return await func();
    }
    catch (error: unknown) {
        if (error instanceof ApiError)
            errorStore.addError(`[${error.code}] ${error.message}` );
        
        return Promise.reject(error);
    }
    finally {
        lastProcess();
    }
}

