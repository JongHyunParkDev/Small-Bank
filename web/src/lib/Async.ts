export async function process(that: any, func: () => void)
{
    that.processingCount++;
    try {
        return await func();
    }
    catch (err) {
        return Promise.reject(err);
    }
    finally {
        that.processingCount--;
    }
}

