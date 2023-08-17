export async function process(firstProcess: () => void, lastProcess: () => void, func: () => void)
{
    firstProcess();
    try {
        return await func();
    }
    catch (err) {
        return Promise.reject(err);
    }
    finally {
        lastProcess();
    }
}

